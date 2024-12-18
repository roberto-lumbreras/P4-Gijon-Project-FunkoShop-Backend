package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.details.DetailOrder;
import org.junit.jupiter.api.Test;

public class OrderDTOTest {

    public OrderDTOTest() {
    }

    @Test
    public void testGetOrderId() {
        System.out.println("getOrderId");
        OrderDTO instance = new OrderDTO();
        Long expResult = null;
        Long result = instance.getOrderId();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetUserId() {
        System.out.println("getUserId");
        OrderDTO instance = new OrderDTO();
        Long expResult = null;
        Long result = instance.getUserId();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOrderDate() {
        System.out.println("getOrderDate");
        OrderDTO instance = new OrderDTO();
        LocalDate expResult = null;
        LocalDate result = instance.getOrderDate();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetIsPaid() {
        System.out.println("getIsPaid");
        OrderDTO instance = new OrderDTO();
        Boolean expResult = null;
        Boolean result = instance.getIsPaid();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetPayment() {
        System.out.println("getPayment");
        OrderDTO instance = new OrderDTO();
        String expResult = null;
        String result = instance.getPayment();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        OrderDTO instance = new OrderDTO();
        Order.Status expResult = null;
        Order.Status result = instance.getStatus();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetProductQuantity() {
        System.out.println("getProductQuantity");
        OrderDTO instance = new OrderDTO();
        Integer expResult = null;
        Integer result = instance.getProductQuantity();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetProductList() {
        System.out.println("getProductList");
        OrderDTO instance = new OrderDTO();
        ArrayList<DetailOrder> expResult = null;
        ArrayList<DetailOrder> result = (ArrayList<DetailOrder>) instance.getProductList();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetTotalAmount() {
        System.out.println("getTotalAmount");
        OrderDTO instance = new OrderDTO();
        Float expResult = null;
        Float result = instance.getTotalAmount();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetOrderId() {
        System.out.println("setOrderId");
        Long orderId = 1L;
        OrderDTO instance = new OrderDTO();
        instance.setOrderId(orderId);
        assertEquals(orderId, instance.getOrderId());
    }

    @Test
    public void testSetUserId() {
        System.out.println("setUserId");
        Long userId = 1L;
        OrderDTO instance = new OrderDTO();
        instance.setUserId(userId);
        assertEquals(userId, instance.getUserId());
    }

    @Test
    public void testSetOrderDate() {
        System.out.println("setOrderDate");
        LocalDate orderDate = LocalDate.now();
        OrderDTO instance = new OrderDTO();
        instance.setOrderDate(orderDate);
        assertEquals(orderDate, instance.getOrderDate());
    }

    @Test
    public void testSetIsPaid() {
        System.out.println("setIsPaid");
        Boolean isPaid = true;
        OrderDTO instance = new OrderDTO();
        instance.setIsPaid(isPaid);
        assertEquals(isPaid, instance.getIsPaid());
    }

    @Test
    public void testSetPayment() {
        System.out.println("setPayment");
        String payment = "Credit Card";
        OrderDTO instance = new OrderDTO();
        instance.setPayment(payment);
        assertEquals(payment, instance.getPayment());
    }

    @Test
    public void testSetStatus() {
        System.out.println("setStatus");
        Order.Status status = Order.Status.PROCESSING;
        OrderDTO instance = new OrderDTO();
        instance.setStatus(status);
        assertEquals(status, instance.getStatus());
    }

    @Test
    public void testSetProductQuantity() {
        System.out.println("setProductQuantity");
        Integer productQuantity = 5;
        OrderDTO instance = new OrderDTO();
        instance.setProductQuantity(productQuantity);
        assertEquals(productQuantity, instance.getProductQuantity());
    }

    @Test
    public void testSetProductList() {
        System.out.println("setProductList");
        ArrayList<DetailOrder> productList = new ArrayList<>();
        OrderDTO instance = new OrderDTO();
        instance.setProductList(productList);
        assertEquals(productList, instance.getProductList());
    }

    @Test
    public void testSetTotalAmount() {
        System.out.println("setTotalAmount");
        Float totalAmount = 100.0f;
        OrderDTO instance = new OrderDTO();
        instance.setTotalAmount(totalAmount);
        assertEquals(totalAmount, instance.getTotalAmount());
    }
}