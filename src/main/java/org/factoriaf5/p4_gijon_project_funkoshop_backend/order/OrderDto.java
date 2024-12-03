package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.order.OrderService.Status;

public class OrderDto {

    private Long orderId;
    private Long userId;
    private Date orderDate;
    private boolean paid;
    private String payment;
    private Status status;
    private Integer amount;
    private List<ArrayList> productList;
    private Float price;

    public OrderDto() {

    }

    public OrderDto(Order order) {

        this.orderId = order.getOrderId();
        this.userId = order.getUser().getId();
        this.orderDate = order.getOrderDate();
        this.paid = order.getIsPaid();
        this.payment = order.getPayment();
        this.status = OrderService.Status.getStatus();
        this.amount = order.getAmount();
        this.productList = order.getProductList();
        this.price = order.getPrice();

    }

    public Status getStatus() {
        return status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public String getPayment() {
        return payment;
    }

    public Integer getAmount() {
        return amount;
    }

    public List<ArrayList> getProductList() {
        return productList;
    }

    public Float getPrice() {
        return price;
    }

}