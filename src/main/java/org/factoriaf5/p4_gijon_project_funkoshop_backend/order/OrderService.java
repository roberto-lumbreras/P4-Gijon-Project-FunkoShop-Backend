package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.details.DetailOrder;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.details.DetailOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.transaction.Transactional;


@Service
public class OrderService {

    public OrderService() {
    }

    public enum Status {
        PENDING, SHIPPED, DELIVERED, CANCELED
    }

    public Status getStatus(Status status) {
        return status;
    }

    public void setStatus(Status status){
        // implement logic to update status
    }
    
    private OrderRepository orderRepository;
    
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public OrderDto getStatus(String authorizationHeader, Long orderId) {
        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtil.generateEmailByToken(token);
        
        User user = userRepository.findUserByEmail(emailToken);
        
        if(user == null) {
            throw new RuntimeException("User not found.");
        }

        if (!token.equals(user.getToken())) {
            throw new RuntimeException("Invalid token.");
        }

        if (!"user".equals(user.getRoles()) && !"admin".equals(user.getRoles())) {
            throw new RuntimeException("Unauthorized user.");
        }

        Optional<Order> order = orderRepository.findById(orderId);

        if (order == null) {
            throw new RuntimeException("Order not found.");
        }

        return Status.getStatus();
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Transactional
    public void updateOrderStatus(String authorizationHeader, Long orderId, Status status) {//naming cambiar al definitivo de updateOder

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid or missing token");
        }

        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtil.generateEmailFromToken(token);

        User user = userRepository.findByEmail(emailToken);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (!token.equals(user.getToken())) {
            throw new IllegalArgumentException("Invalid token");
        }

        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        order.setStatus(status);
        orderRepository.save(order);
    }

    @Autowired
    private DetailOrderRepository detailOrderRepository;
    @Transactional
    public byte[] generateOrderPDF(String authorizationHeader, Long orderId) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid or missing token");
        }

        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtil.generateEmailFromToken(token);
        User user = userRepository.findByEmail(emailToken);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (!token.equals(user.getToken())) {
            throw new IllegalArgumentException("Invalid token");
        }

        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new IllegalArgumentException("Order not found");
        }

        Order order = orderOptional.get();
        List<DetailOrder> details = detailOrderRepository.findByOrderId(order.getOrderId());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            document.add(new Paragraph("Order Invoice"));
            document.add(new Paragraph("Order ID: " + order.getOrderId()));
            document.add(new Paragraph("Order Status: " + order.getStatus()));
            document.add(new Paragraph("Order Details:"));

            for (DetailOrder detail : details) {
                document.add(new Paragraph("Product ID: " + detail.getProductId()));
                document.add(new Paragraph("Quantity: " + detail.getAmount()));
                document.add(new Paragraph("Price: " + detail.getPrice()));
            }

            document.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error generating PDF: " + e.getMessage());
        }

        return byteArrayOutputStream.toByteArray();
    }
}









    


