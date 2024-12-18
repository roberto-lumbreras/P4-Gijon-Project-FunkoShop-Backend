package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.details.DetailOrder;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.order.Order.Status;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.Product;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.ProductDTO;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.ProductRepository;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.Role;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.User;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    public OrderDTO createOrder(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(orderDTO.getOrderDate() != null ? orderDTO.getOrderDate() : LocalDate.now());
        order.setIsPaid(orderDTO.getIsPaid());
        order.setPayment(orderDTO.getPayment());
        order.setStatus(orderDTO.getStatus() != null ? orderDTO.getStatus() : Order.Status.PENDING);
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setProductList(orderDTO.getProductList());
        order.setProductQuantity(orderDTO.getProductQuantity());

        Order savedOrder = orderRepository.save(order);
        return new OrderDTO(savedOrder);
    }

    @Transactional
    public void updateOrderStatus(Long orderId, Status status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado con ID: " + orderId));

        order.setStatus(status);
        orderRepository.save(order);
    }

    public Status getStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found."));
        return order.getStatus();
    }

    @Transactional
    public byte[] generateOrderPDFId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            document.add(new Paragraph("Order Invoice"));
            document.add(new Paragraph("Order ID: " + order.getOrderId()));
            document.add(new Paragraph("Order Status: " + order.getStatus()));
            document.add(new Paragraph("Order Date: " + order.getOrderDate()));
            document.add(new Paragraph("Total Amount: " + order.getTotalAmount()));

            document.add(new Paragraph("Order Details:"));
            for (DetailOrder detail : order.getProductList()) {
                document.add(new Paragraph("- Product: " + detail.getProduct().getName()));
                document.add(new Paragraph("  Quantity: " + detail.getQuantity()));
                document.add(new Paragraph("  Price: " + detail.getPrice()));
            }
            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF for order ID: " + orderId, e);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> list = orderRepository.findAll();
        return list.stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> listByMonth() {
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfLastMonth = now.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfLastMonth = now.withDayOfMonth(1).minusDays(1);
        List<OrderDTO> OrderDTO = orderRepository.findAll().stream().filter(order -> {
            LocalDate orderDate = order.getOrderDate();
            return !orderDate.isBefore(firstDayOfLastMonth) && !orderDate.isAfter(lastDayOfLastMonth);
        }).map(order -> new OrderDTO(order)).collect(Collectors.toList());
        return OrderDTO;
    }

    public List<ProductDTO> getBestSellers() {
        Map<Long, Integer> productSalesMap = new HashMap<>();
        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
            for (DetailOrder detailOrder : order.getProductList()) {
                Long productId = detailOrder.getProduct().getId();
                int quantity = detailOrder.getQuantity();

                productSalesMap.put(productId, productSalesMap.getOrDefault(productId, 0) + quantity);
            }
        }
        List<Map.Entry<Long, Integer>> sortedProductList = productSalesMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toList());

        List<ProductDTO> bestSellers = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : sortedProductList) {
            Long productId = entry.getKey();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            bestSellers.add(new ProductDTO(product));
        }
        return bestSellers;
    }

    public byte[] generatePDFAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new IllegalArgumentException("No orders found in the database.");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            for (Order order : orders) {
                document.add(new Paragraph("Order Invoice"));
                document.add(new Paragraph("Order ID: " + order.getOrderId()));
                document.add(new Paragraph("Order Status: " + order.getStatus()));
                document.add(new Paragraph("Order Date: " + order.getOrderDate()));
                document.add(new Paragraph("Total Amount: " + order.getTotalAmount()));
                document.add(new Paragraph("Order Details:"));
                for (DetailOrder detail : order.getProductList()) {
                    document.add(new Paragraph("- Product: " + detail.getProduct().getName()));
                    document.add(new Paragraph("  Quantity: " + detail.getQuantity()));
                    document.add(new Paragraph("  Price: " + detail.getPrice()));
                }
                document.add(new Paragraph("\n"));
            }

            document.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error generating PDF: " + e.getMessage());
        }
        return byteArrayOutputStream.toByteArray();
    }

    @SuppressWarnings("ConvertToStringSwitch")
    public List<OrderDTO> listOrdersByUser(User authenticatedUser) {
        if (authenticatedUser == null) {
            throw new IllegalArgumentException("Usuario no autenticado");
        }
        Role role = authenticatedUser.getRole();
        if ("ROLE_ADMIN".equals(role.name())) {
            return orderRepository.findAll().stream()
                    .map(order -> new OrderDTO(order))
                    .collect(Collectors.toList());
        } else if ("ROLE_USER".equals(role.name())) {
            return orderRepository.findByUserId(authenticatedUser.getId()).stream()
                    .map(order -> new OrderDTO(order))
                    .collect(Collectors.toList());
        } else {
            throw new SecurityException("Acceso denegado. Solo un USER o un ADMIN pueden listar pedidos");
        }
    }
}