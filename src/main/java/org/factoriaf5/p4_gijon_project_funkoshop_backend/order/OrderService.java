package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.details.DetailOrder;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.details.DetailOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    private OrderRepository orderRepository;
    private DetailOrderRepository detailOrderRepository;

    public enum Status {
        PENDING, PROCESSING, DELIVERED, CANCELED
    }

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, JwtUtil jwtUtil,
            DetailOrderRepository detailOrderRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.detailOrderRepository = detailOrderRepository;

    }

    public OrderService() {
    }

    private Order initializeOrder(OrderDto orderDto) {
        Order order = new Order();

        order.setUser(orderDto.getUserId());
        order.setOrderDate(orderDto.getOrderDate());
        order.setPayment(orderDto.getPayment());
        order.setIsPaid(confirmPaid(orderDto.getPayment()));
        order.setStatus(Status.PROCESSING);
        order.setTotalAmount(orderDto.getTotalAmount());
        order.setPrice(orderDto.getPrice());

        return order;
    }

    @Transactional
    public Order createOrder(@RequestHeader("Authorization") String authorizationHeader, OrderDto orderDto) {
        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtil.generateEmailByToken(token);

        User user = userRepository.findByEmail(emailToken)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!user.getToken().equals(token)) {
            throw new SecurityException("El token enviado no coincide con el que tiene el usuario");
        }

        if (!"user".equals(user.getRoles()) && !"admin".equals(user.getRoles())) {
            throw new IllegalArgumentException("Acceso denegado. Solo un USER o un ADMIN pueden crear un pedido");
        }

        validatePaymentMethod(orderDto.getPayment());

        Order order = initializeOrder(orderDto);
        Order savedOrder = orderRepository.save(order);

        List<DetailOrder> productList = mapDetails(orderDto.getProductList(), savedOrder);

        detailOrderRepository.saveAll(productList);
        savedOrder.setProductList(productList);

        orderRepository.save(savedOrder);
    }

    private List<DetailOrder> mapDetails(List<DetailOrder> productList, Order savedOrder) {
        return productList.stream()
                .map(detailDto -> {
                    DetailOrder detailOrder = new DetailOrder();

                    detailOrder.setProductQuantity(detailDto.getProductQuantity());
                    detailOrder.setPrice(detailDto.getPrice());
                    detailOrder.setOrder(savedOrder);
                    detailOrder.setProduct(productList);

                    return detailOrder;
                })
                .collect(Collectors.toList());
    }

    private boolean confirmPaid(String payment) {
        return "tarjeta".equalsIgnoreCase(payment);
    }

    private void validatePaymentMethod(String payment) {
        if (!"contrareembolso".equalsIgnoreCase(payment) && !"tarjeta".equalsIgnoreCase(payment)) {
            throw new IllegalArgumentException("El método de pago debe ser 'contrareembolso' o 'tarjeta'.");
        }
    }

    public List<OrderDto> listOrdersByUser(String authorizationHeader, OrderDto orderDto) {
        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtil.generateEmailByToken(token);

        User user = userRepository.findByEmail(emailToken)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!user.getToken().equals(token)) {
            throw new SecurityException("El token enviado no coincide con el que tiene el usuario");
        }

        if (!"user".equals(user.getRoles()) && !"admin".equals(user.getRoles())) {
            throw new IllegalArgumentException("Acceso denegado. Solo un USER o un ADMIN pueden crear un pedido");
        }

        List<Order> listOrdersFromUser = orderRepository.findByUserId(orderDto.getUserId());

        return listOrdersFromUser.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getAllOrders(String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtil.generateEmailByToken(token);

        User user = userRepository.findByEmail(emailToken)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!user.getToken().equals(token)) {
            throw new SecurityException("El token enviado no coincide con el que tiene el usuario");
        }

        if (!"user".equals(user.getRoles()) && !"admin".equals(user.getRoles())) {
            throw new IllegalArgumentException("Acceso denegado. Solo un USER o un ADMIN pueden crear un pedido");
        }

        List<Order> list = orderRepository.findAll();

        return list.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }

    public Status getStatus(String authorizationHeader, Long orderId) {
        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtil.generateEmailByToken(token);

        User user = userRepository.findUserByEmail(emailToken);

        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        if (!token.equals(user.getToken())) {
            throw new SecurityException("Invalid token.");
        }

        if (!"user".equals(user.getRoles()) && !"admin".equals(user.getRoles())) {
            throw new IllegalArgumentException("Unauthorized user.");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found."));

        if (order == null) {
            throw new IllegalArgumentException("Order not found.");
        }

        return order.getStatus();
    }

    @Transactional
    public void updateOrderStatus(String authorizationHeader, Long orderId, Status status) {
        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtil.generateEmailFromToken(token);

        User user = userRepository.findByEmail(emailToken);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (!token.equals(user.getToken())) {
            throw new SecurityException("Invalid token");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        order.setStatus(status);
        orderRepository.save(order);
    }

    @Autowired
    @Transactional
    public byte[] generateOrderPDF(String authorizationHeader, Long orderId) {
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
                document.add(new Paragraph("Product ID: " + detail.getProduct()));
                document.add(new Paragraph("Product Quantity: " + detail.getProductQuantity()));
                document.add(new Paragraph("Price: " + detail.getPrice()));
            }

            document.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error generating PDF: " + e.getMessage());
        }

        return byteArrayOutputStream.toByteArray();
    }
}
