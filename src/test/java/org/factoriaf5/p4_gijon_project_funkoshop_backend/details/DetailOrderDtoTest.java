/* package org.factoriaf5.p4_gijon_project_funkoshop_backend.details;

import static org.junit.jupiter.api.Assertions.*;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DetailOrderDtoTest {

    private DetailOrder mockDetailOrder;
    private Product mockProduct;
    private Order mockOrder;

    @BeforeEach
    void setUp() {
        mockProduct = new Product();
        mockProduct.setProductId(1L);
        
        mockOrder = new Order();
        mockOrder.setOrderId(2L);
        
        mockDetailOrder = new DetailOrder();
        mockDetailOrder.setDetailId(3L);
        mockDetailOrder.setProduct(mockProduct);
        mockDetailOrder.setOrder(mockOrder);
        mockDetailOrder.setAmount(5);
        mockDetailOrder.setPrice(29.99);
    }

    @Test
    void testDefaultConstructor() {
        DetailOrderDto detailOrderDto = new DetailOrderDto();
        assertNotNull(detailOrderDto);
    }

    @Test
    void testParameterizedConstructor() {
        DetailOrderDto detailOrderDto = new DetailOrderDto(mockDetailOrder);
        
        assertEquals(3L, detailOrderDto.getDetailId());
        assertEquals(1L, detailOrderDto.getProductId());
        assertEquals(2L, detailOrderDto.getOrderId());
        assertEquals(5, detailOrderDto.getAmount());
        assertEquals(29.99, detailOrderDto.getPrice());
    }

    @Test
    void testGetters() {
        DetailOrderDto detailOrderDto = new DetailOrderDto(mockDetailOrder);
        
        assertEquals(3L, detailOrderDto.getDetailId());
        assertEquals(1L, detailOrderDto.getProductId());
        assertEquals(2L, detailOrderDto.getOrderId());
        assertEquals(5, detailOrderDto.getAmount());
        assertEquals(29.99, detailOrderDto.getPrice());
    }

    @Test
    void testConstructorWithDifferentValues() {
        Product anotherProduct = new Product();
        anotherProduct.setProductId(10L);
        
        Order anotherOrder = new Order();
        anotherOrder.setOrderId(20L);
        
        DetailOrder anotherDetailOrder = new DetailOrder();
        anotherDetailOrder.setDetailId(30L);
        anotherDetailOrder.setProduct(anotherProduct);
        anotherDetailOrder.setOrder(anotherOrder);
        anotherDetailOrder.setAmount(3);
        anotherDetailOrder.setPrice(19.99);
        
        DetailOrderDto detailOrderDto = new DetailOrderDto(anotherDetailOrder);
        
        assertEquals(30L, detailOrderDto.getDetailId());
        assertEquals(10L, detailOrderDto.getProductId());
        assertEquals(20L, detailOrderDto.getOrderId());
        assertEquals(3, detailOrderDto.getAmount());
        assertEquals(19.99, detailOrderDto.getPrice());
    }

    @Test
    void testNullValues() {
        DetailOrder nullDetailOrder = new DetailOrder();
        DetailOrderDto detailOrderDto = new DetailOrderDto(nullDetailOrder);
        
        assertNull(detailOrderDto.getDetailId());
        assertNull(detailOrderDto.getProductId());
        assertNull(detailOrderDto.getOrderId());
        assertNull(detailOrderDto.getAmount());
        assertNull(detailOrderDto.getPrice());
    }
} */