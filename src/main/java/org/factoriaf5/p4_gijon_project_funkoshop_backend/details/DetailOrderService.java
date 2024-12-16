package org.factoriaf5.p4_gijon_project_funkoshop_backend.details;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.order.Order;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.order.OrderDto;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.order.OrderRepository;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class DetailOrderService {

    // UserRepository userRepository;
    OrderRepository orderRepository;

    public List<OrderDto> listByMonth(String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtil.extractEmailFromToken(token);


        User user = userRepository.findByEmail(emailToken)
        .orElseThrow(() -> new IllegalArgumentException("Usuario del token no encontrado"));


        if (!token.equals(user.getToken(token)) && !"admin".equals(user.getRoles())) {
        throw new SecurityException("Acceso denegado. Solo el ADMIN puede realizar la acción");
        }

        LocalDate now = LocalDate.now();

        LocalDate firstDayOfLastMonth = now.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfLastMonth = now.withDayOfMonth(1).minusDays(1);
            List<OrderDto> orderDto = OrderRepository.findAll().stream().filter(order -> {LocalDate orderDate = order.getOrderDate(); 
            return !orderDate.isBefore(firstDayOfLastMonth) && !orderDate.isAfter(lastDayOfLastMonth);}).map(order -> new OrderDto(order)).collect(Collectors.toList());
        return orderDto;
    }

    public List<ProductDto> retrieveBestSellers(String authorizationHeader) {
    
    String token = authorizationHeader.substring(7);
    String emailToken = jwtUtil.extractEmailFromToken(token);

    User user = userRepository.findByEmail(emailToken)
        .orElseThrow(() -> new IllegalArgumentException("Usuario del token no encontrado"));

    if (!token.equals(user.getToken(token)) && !"admin".equals(user.getRoles())) {
        throw new SecurityException("Acceso denegado. Solo el ADMIN puede realizar la acción");
    }

    // Mapa para almacenar la cantidad total de cada producto
    Map<Long, Integer> productSalesMap = new HashMap<>();

    
    List<Order> orders = orderRepository.findAll();

    // Recorrer todas las órdenes y contar la cantidad de cada producto vendido

    for (Order order : orders) {

        DetailOrderDto detailOrderDto;
        
        for (DetailOrder detailOrder : order.getProductList()) {
            Long productId =detailOrderDto.getProduct().getId();  
            int quantity = detailOrderDto.getProductQuantity();          

            productSalesMap.put(productId, productSalesMap.getOrDefault(productId, 0) + quantity);
        }
    }

    // Ordenar los productos por la cantidad total (de mayor a menor)

        List<Map.Entry<Long, Integer>> sortedProductList = productSalesMap.entrySet().stream()
        .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())).collect(Collectors.toList());

    // Convertir los productos ordenados en una lista de DTO

        List<ProductDto> bestSellers = new ArrayList();
        for (Map.Entry<Long, Integer> entry : sortedProductList) {
        Long productId = entry.getKey();
        Integer totalQuantity = entry.getValue();

        // Obtener detalles del producto
        Product product = productRepository.findById(productId)
        .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // agregar los datos que consideres necesarios en ProductDto
        bestSellers.add(new ProductDto(productId, totalQuantity));
    }

    return bestSellers;
    }

    public byte[] generatePDFAllOrders(String authorizationHeader, DetailOrderDto detailOrderDto) {
        
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
            document.add(new Paragraph("Order Details:"+ order.toString()));

        }
            document.close();

        } catch (Exception e) {
            throw new IllegalArgumentException("Error generating PDF: " + e.getMessage());
        }

        return byteArrayOutputStream.toByteArray();

    }

    public Integer calculateQuantity(String authorizationHeader , Order orderId , Product productId) {
    
        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtil.extractEmailFromToken(token);
        
        User user = userRepository.findByEmail(emailToken)
                .orElseThrow(() -> new IllegalArgumentException("Usuario del token no encontrado"));
        
        if (!token.equals(user.getToken(token)) && !"admin".equals(user.getRoles())) {
            throw new SecurityException("Acceso denegado. Solo el ADMIN puede realizar la acción");
        }
        
        // Mapa para almacenar la cantidad total de cada producto
        Map<Product, Integer> quantityMap = new HashMap<>();
            
        List<DetailOrder> detailOrders = detailOrderRepository.findAll();
        
        DetailOrderDto detailOrderDto;
                
        for (Product product : product.getProductId()) {
            for (DetailOrder detailOrder : detailOrder.getDetailIdList()) {

                Long productId = product.getId(); 
                int quantity = detailOrderDto.getProductQuantity();          
        
                quantityMap.put(productId, quantityMap.getOrDefault(productId, 0) + quantity);

            }
        }
        
        return quantityMap.toArray;
    }




}
