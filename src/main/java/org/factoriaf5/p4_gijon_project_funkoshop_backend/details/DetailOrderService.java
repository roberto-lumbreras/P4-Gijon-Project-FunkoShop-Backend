package org.factoriaf5.p4_gijon_project_funkoshop_backend.details;

import java.util.List;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.order.Order;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.order.OrderRepository;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

@Service
public class DetailOrderService {

    // UserRepository userRepository;
    OrderRepository orderRepository;

    public List<DetailOrderDto> getSales(String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtil.extractEmailFromToken(token);

        User user = userRepository.findByEmail(emailToken)
                .orElseThrow(() -> new IllegalArgumentException("Usuario del token no encontrado"));

        if (!token.equals(user.getToken(token)) && !"admin".equals(user.getRoles())) {
            throw new SecurityException("Acceso denegado. Solo el ADMIN puede realizar la acción");
        }

        List<Order> order = orderRepository.findAll().stream().filter();

    }
}
