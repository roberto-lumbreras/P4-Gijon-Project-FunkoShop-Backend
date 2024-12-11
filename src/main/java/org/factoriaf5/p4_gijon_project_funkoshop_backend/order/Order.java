package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import java.time.LocalDate;
import java.util.List;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.details.DetailOrder;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "paid", nullable = false)
    private Boolean isPaid;

    @Column(name = "payment_method", nullable = false)
    private String payment;

    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "totalAmount", nullable = false)
    private Integer totalAmount;

    @Column(name = "product_list", nullable = false)
    private List<DetailOrder> productList;

    @Column(name = "price", nullable = false)
    private Float price;

    public Order(Long orderId, User user, LocalDate orderDate, Boolean isPaid, Status status, int totalAmount,
            List<DetailOrder> productList, Float price) {
        this.orderId = orderId;
        this.user = user;
        this.orderDate = orderDate;
        this.isPaid = isPaid;
        this.status = status;
        this.totalAmount = totalAmount;
        this.productList = productList;
        this.price = price;
    }

    public Order() {

    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<DetailOrder> getProductList() {
        return productList;
    }

    public void setProductList(List<DetailOrder> productList) {
        this.productList = productList;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public enum Status {
        PENDING, PROCESSING, DELIVERED, CANCELED
    }

}
