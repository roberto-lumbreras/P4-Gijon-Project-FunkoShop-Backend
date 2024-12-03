package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;

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

}
