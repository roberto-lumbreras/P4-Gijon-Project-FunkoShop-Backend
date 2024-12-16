/* package org.factoriaf5.p4_gijon_project_funkoshop_backend.details;

import static org.junit.jupiter.api.Assertions.*;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DetailOrderTest {

    private DetailOrder detailOrder;
    private Product mockProduct;
    private Order mockOrder;

    @BeforeEach
    void setUp() {
        mockProduct = new Product(); // Asumo que existe la clase Product
        mockOrder = new Order(); // Asumo que existe la clase Order
        detailOrder = new DetailOrder();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(detailOrder);
    }

    @Test
    void testParameterizedConstructor() {
        DetailOrder fullDetailOrder = new DetailOrder(1L, mockProduct, mockOrder, 2, 19.99);
        
        assertEquals(1L, fullDetailOrder.getDetailId());
        assertEquals(mockProduct, fullDetailOrder.getProduct());
        assertEquals(mockOrder, fullDetailOrder.getOrder());
        assertEquals(2, fullDetailOrder.getAmount());
        assertEquals(19.99, fullDetailOrder.getPrice());
    }

    @Test
    void testSettersAndGetters() {
        // Probando setDetailId
        detailOrder.setDetailId(5L);
        assertEquals(5L, detailOrder.getDetailId());

        // Probando setProduct
        detailOrder.setProduct(mockProduct);
        assertEquals(mockProduct, detailOrder.getProduct());

        // Probando setOrder
        detailOrder.setOrder(mockOrder);
        assertEquals(mockOrder, detailOrder.getOrder());

        // Probando setAmount
        detailOrder.setAmount(3);
        assertEquals(3, detailOrder.getAmount());

        // Probando setPrice
        detailOrder.setPrice(29.99);
        assertEquals(29.99, detailOrder.getPrice());
    }

    @Test
    void testNullValues() {
        DetailOrder nullDetailOrder = new DetailOrder();
        
        assertNull(nullDetailOrder.getDetailId());
        assertNull(nullDetailOrder.getProduct());
        assertNull(nullDetailOrder.getOrder());
        assertNull(nullDetailOrder.getAmount());
        assertNull(nullDetailOrder.getPrice());
    }

    @Test
    void testDifferentInstanceValues() {
        DetailOrder detailOrder1 = new DetailOrder(1L, mockProduct, mockOrder, 2, 19.99);
        DetailOrder detailOrder2 = new DetailOrder(2L, mockProduct, mockOrder, 3, 29.99);

        assertNotEquals(detailOrder1.getDetailId(), detailOrder2.getDetailId());
        assertEquals(detailOrder1.getProduct(), detailOrder2.getProduct());
        assertEquals(detailOrder1.getOrder(), detailOrder2.getOrder());
        assertNotEquals(detailOrder1.getAmount(), detailOrder2.getAmount());
        assertNotEquals(detailOrder1.getPrice(), detailOrder2.getPrice());
    }
} */