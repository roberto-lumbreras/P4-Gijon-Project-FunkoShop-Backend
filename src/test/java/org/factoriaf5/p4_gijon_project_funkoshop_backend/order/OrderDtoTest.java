package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderDtoTest {

    private OrderDto orderDto;
    private Order mockOrder;

    @BeforeEach
    void setUp() {
        // Crear un mock de Order para las pruebas
        mockOrder = new Order();
        mockOrder.setId(1L);
        
        User mockUser = new User();
        mockUser.setId(100L);
        mockOrder.setUser(mockUser);
        
        mockOrder.setOrderDate(new Date());
        mockOrder.setIsPaid(true);
        mockOrder.setStatus(Status.PROCESSING);
        mockOrder.setAmount(5);
        
        List<ArrayList> mockProductList = new ArrayList<>();
        mockOrder.setProductList(mockProductList);
        
        mockOrder.setPrice(99.99f);

        // Crear OrderDto usando el constructor con Order
        orderDto = new OrderDto(mockOrder);
    }

    @Test
    void testConstructorFromOrder() {
        assertEquals(1L, orderDto.getId());
        assertEquals(100L, orderDto.getUser());
        assertNotNull(orderDto.getOrderDate());
        assertTrue(orderDto.isPaid());
        assertEquals(Status.PROCESSING, orderDto.getStatus());
        assertEquals(5, orderDto.getAmount());
        assertNotNull(orderDto.getProductList());
        assertEquals(99.99f, orderDto.getPrice(), 0.001);
    }

    @Test
    void testSettersAndGetters() {
        // Probar cada setter y getter
        orderDto.setId(2L);
        assertEquals(2L, orderDto.getId());

        User newUser = new User();
        newUser.setId(200L);
        orderDto.setUser(newUser);
        assertEquals(newUser, orderDto.getUser());

        Date newDate = new Date();
        orderDto.setOrderDate(newDate);
        assertEquals(newDate, orderDto.getOrderDate());

        orderDto.setPaid(false);
        assertFalse(orderDto.isPaid());

        orderDto.setAmount(10);
        assertEquals(10, orderDto.getAmount());

        List<ArrayList> newProductList = new ArrayList<>();
        orderDto.setProductList(newProductList);
        assertEquals(newProductList, orderDto.getProductList());

        orderDto.setPrice(149.99f);
        assertEquals(149.99f, orderDto.getPrice(), 0.001);
    }

    @Test
    void testDefaultConstructor() {
        OrderDto emptyOrderDto = new OrderDto();
        assertNotNull(emptyOrderDto);
    }
}