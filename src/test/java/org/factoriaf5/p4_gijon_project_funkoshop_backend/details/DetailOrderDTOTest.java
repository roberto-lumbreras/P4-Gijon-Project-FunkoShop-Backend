package org.factoriaf5.p4_gijon_project_funkoshop_backend.details;

import static org.junit.jupiter.api.Assertions.*;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.Product;
import org.junit.jupiter.api.Test;

public class DetailOrderDTOTest {

    public DetailOrderDTOTest() {
    }

    @Test
    public void testGetDetailId() {
        System.out.println("getDetailId");
        DetailOrderDTO instance = new DetailOrderDTO();
        Long expResult = null;
        Long result = instance.getDetailId();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetProduct() {
        System.out.println("getProduct");
        DetailOrderDTO instance = new DetailOrderDTO();
        Product expResult = null;
        Product result = instance.getProduct();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetProductQuantity() {
        System.out.println("getProductQuantity");
        DetailOrderDTO instance = new DetailOrderDTO();
        Integer expResult = null;
        Integer result = instance.getProductQuantity();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetPrice() {
        System.out.println("getPrice");
        DetailOrderDTO instance = new DetailOrderDTO();
        Double expResult = null;
        Double result = instance.getPrice();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetDetailId() {
        System.out.println("setDetailId");
        Long detailId = 1L;
        DetailOrderDTO instance = new DetailOrderDTO();
        instance.setDetailId(detailId);
        assertEquals(detailId, instance.getDetailId());
    }

    @Test
    public void testSetProduct() {
        System.out.println("setProduct");
        Product product = new Product();
        DetailOrderDTO instance = new DetailOrderDTO();
        instance.setProduct(product);
        assertEquals(product, instance.getProduct());
    }

    @Test
    public void testSetProductQuantity() {
        System.out.println("setProductQuantity");
        Integer productQuantity = 5;
        DetailOrderDTO instance = new DetailOrderDTO();
        instance.setProductQuantity(productQuantity);
        assertEquals(productQuantity, instance.getProductQuantity());
    }

    @Test
    public void testSetPrice() {
        System.out.println("setPrice");
        Double price = 100.0;
        DetailOrderDTO instance = new DetailOrderDTO();
        instance.setPrice(price);
        assertEquals(price, instance.getPrice());
    }

    @Test
    public void testConstructorWithDetailOrder() {
        System.out.println("Constructor with DetailOrder");
        DetailOrder detailOrder = new DetailOrder();
        detailOrder.setId(1L);
        detailOrder.setProduct(new Product());
        detailOrder.setQuantity(10);
        detailOrder.setPrice(200.0);

        DetailOrderDTO instance = new DetailOrderDTO(detailOrder);
        assertEquals(1L, instance.getDetailId());
        assertEquals(detailOrder.getProduct(), instance.getProduct());
        assertEquals(10, instance.getProductQuantity());
        assertEquals(200.0, instance.getPrice());
    }
}