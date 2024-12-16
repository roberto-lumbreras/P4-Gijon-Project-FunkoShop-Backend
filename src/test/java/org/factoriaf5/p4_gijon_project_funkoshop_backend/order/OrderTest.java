/* package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;


public class OrderTest {
@Test

void testGettersAndSetters() {
    // Given
    Order order = new Order();
    User user = new User();


    // When
    order.setId(1L);
    order.setUser(id: 2L, name: "John Doe", email: "john@example.com");
    order.setOrderDate(Date.valueOf("2024-11-28"));
    order.setIsPaid(true);
    order.setStatus(OrderStatus.PENDING);
    order.setAmount(2);
    order.setPrice(3.0F);
    order.setProductList(List.of());

    // Then
    assertEquals(1L, order.getId());
    assertEquals(user, order.getUser());
    assertEquals("2024-11-28", order.getOrderDate());
    assertEquals(true, order.getIsPaid());
    assertEquals(OrderStatus.PENDING, order.getStatus());
    assertEquals(2, order.getAmount());
    assertEquals(3.0F, order.getPrice());
    assertEquals(List.of(), order.get());
}
}
 */