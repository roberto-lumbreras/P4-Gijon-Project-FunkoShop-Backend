package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.catalina.User;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.details.DetailOrder;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.details.DetailOrderRepository;
import org.springframework.stereotype.Service;

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

}
