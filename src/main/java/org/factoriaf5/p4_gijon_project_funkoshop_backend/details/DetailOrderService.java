package org.factoriaf5.p4_gijon_project_funkoshop_backend.details;

import java.util.List;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.order.Order;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.order.OrderDto;
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

    public List<OrderDto> listByMonth(String authorizationHeader) {
        String token = authorizationHeader.substring(7);  // Extrae el token
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

}
