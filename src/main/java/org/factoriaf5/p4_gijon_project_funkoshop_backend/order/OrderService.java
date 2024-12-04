package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.catalina.User;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.details.DetailOrder;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.details.DetailOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private DetailOrderRepository detailOrderRepository;

    public OrderService() {
    }

    public enum Status {
        PENDING, PROCESSING, DELIVERED, CANCELED
    }
    
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private Order initializeOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setUser(orderDto.getUserId());
        order.setOrderDate(new Date());
        order.setPayment(orderDto.getPayment());
        order.setStatus(Status.PROCESSING);
        order.setIsPaid(determineIsPaid(orderDto.getPayment())); 
        order.setAmount(orderDto.getAmount());
        order.setPrice(orderDto.getPrice());
        return order;
    }

    private boolean determineIsPaid(String payment) {
        return "tarjeta".equalsIgnoreCase(payment); 
    }

    private void validatePaymentMethod(String payment) {
        if (!"contrareembolso".equalsIgnoreCase(payment) && !"tarjeta".equalsIgnoreCase(payment)) {
            throw new IllegalArgumentException("El método de pago debe ser 'contrareembolso' o 'tarjeta'.")
        }
    } 

    @Transactional
    public void createOrder(OrderDto orderDto) {
        validatePaymentMethod(orderDto.getPayment());
        Order order = initializeOrder(orderDto);
        Order savedOrder = orderRepository.save(order);
        List<DetailOrder> productList = mapDetails(orderDto.getProductList(), savedOrder);
        detailOrderRepository.saveAll(productList);
        savedOrder.setProductList(productList); }
    private List<DetailOrder> mapDetails(List<DetailOrder> productList, Order savedOrder) {
            return productList.stream()
                .map(detailDto -> {
                    DetailOrder detailOrder = new DetailOrder();
                    detailOrder.setAmount(detailDto.getAmount());
                    detailOrder.setPrice(detailDto.getPrice());
                    detailOrder.setOrder(savedOrder);
                    return detailOrder;
                })
                .collect(Collectors.toList());
    }


    public List<Order> listOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
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









    


