package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.configuration.jwtoken.JwtUtils;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.details.DetailOrder;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.details.DetailOrderDto;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.details.DetailOrderRepository;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.Product;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.ProductDTO;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.User;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DetailOrderRepository detailOrderRepository;

    @Transactional
    public Order createOrder(OrderDto orderDto) {
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

/*     private boolean confirmPaid(String payment) {
        return "tarjeta".equalsIgnoreCase(payment);
    }

    private void validatePaymentMethod(String payment) {
        if (!"contrareembolso".equalsIgnoreCase(payment) && !"tarjeta".equalsIgnoreCase(payment)) {
            throw new IllegalArgumentException("El método de pago debe ser 'contrareembolso' o 'tarjeta'.");
        }
    } */

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

    public List<OrderDto> listByMonth(String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtil.getEmailFromJwtToken(token);

        User user = userRepository.findByEmail(emailToken)
                .orElseThrow(() -> new IllegalArgumentException("Usuario del token no encontrado"));

        if (!token.equals(user.getJwToken()) && !"admin".equals(user.getRole().name())) {
            throw new SecurityException("Acceso denegado. Solo el ADMIN puede realizar la acción");
        }

        LocalDate now = LocalDate.now();

        LocalDate firstDayOfLastMonth = now.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfLastMonth = now.withDayOfMonth(1).minusDays(1);
        List<OrderDto> orderDto = orderRepository.findAll().stream().filter(order -> {
            LocalDate orderDate = order.getOrderDate();
            return !orderDate.isBefore(firstDayOfLastMonth) && !orderDate.isAfter(lastDayOfLastMonth);
        }).map(order -> new OrderDto(order)).collect(Collectors.toList());
        return orderDto;
    }

    public List<ProductDTO> getBestSellers(String authorizationHeader) {

        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtil.getEmailFromJwtToken(token);

        User user = userRepository.findByEmail(emailToken)
                .orElseThrow(() -> new IllegalArgumentException("Usuario del token no encontrado"));

        if (!token.equals(user.getJwToken()) && !"admin".equals(user.getRole().name())) {
            throw new SecurityException("Acceso denegado. Solo el ADMIN puede realizar la acción");
        }

        // Mapa para almacenar la cantidad total de cada producto
        Map<Long, Integer> productSalesMap = new HashMap<>();

        List<Order> orders = orderRepository.findAll();

        // Recorrer todas las órdenes y contar la cantidad de cada producto vendido

        for (Order order : orders) {

            for (DetailOrder detailOrder : order.getProductList()) {
                Long productId = detailOrder.getProduct().getId();
                int quantity = detailOrder.getProductQuantity();

                productSalesMap.put(productId, productSalesMap.getOrDefault(productId, 0) + quantity);
            }
        }

        // Ordenar los productos por la cantidad total (de mayor a menor)

        List<Map.Entry<Long, Integer>> sortedProductList = productSalesMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toList());

        // Convertir los productos ordenados en una lista de DTO

        List<ProductDTO> bestSellers = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : sortedProductList) {
            Long productId = entry.getKey();

            // Obtener detalles del producto
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            // agregar los datos que consideres necesarios en ProductDto
            bestSellers.add(new ProductDTO(product));
        }

        return bestSellers;
    }

    public byte[] generatePDFAllOrders(String authorizationHeader, DetailOrderDto detailOrderDto) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid or missing token");
        }

        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtil.getEmailFromJwtToken(token);
        User user = userRepository.findByEmail(emailToken).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (!token.equals(user.getJwToken())) {
            throw new IllegalArgumentException("Invalid token");
        }

        List<Order> orders = orderRepository.findAll();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {

            Document document = new Document();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            for (Order order : orders) {

                document.add(new Paragraph("Order Invoice"));
                document.add(new Paragraph("Order ID: " + order.getOrderId()));
                document.add(new Paragraph("Order Status: " + order.getStatus()));
                document.add(new Paragraph("Order Details:" + order.toString()));

            }
            document.close();

        } catch (Exception e) {
            throw new IllegalArgumentException("Error generating PDF: " + e.getMessage());
        }

        return byteArrayOutputStream.toByteArray();

    }

    public void sendEmail(String authorizationHeader, DetailOrderDto detailOrderDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendEmail'");
    }

}
