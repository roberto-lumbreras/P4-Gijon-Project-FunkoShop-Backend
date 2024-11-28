package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    
    private OrderRepository orderRepository;
    
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getById() {
        return orderRepository.findById(long);
    }
}
