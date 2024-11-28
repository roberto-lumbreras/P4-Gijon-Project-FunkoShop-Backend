package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class OrderDto {

    private Long id;
    private User user;
    private Date orderDate;
    private boolean paid;
    private Status status;
    private Integer amount;
    private List<ArrayList> productList;
    private Float price;

public OrderDto() {

}

public OrderDto(Order order) {

this.id = order.getId();
this.user = order.getUser().getId();
this.orderDate = order.getOrderDate();
this.isPaid = order.getIsPaid();
this.status = OrderService.Status.getStatus();
this.amount = order.getAmount();
this.productList = order.getProductList();
this.price = order.getPrice();

}

public Status getStatus() {
    return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public User getUser() {
    return user;
}

public void setUser(User user) {
    this.user = user;
}

public Date getOrderDate() {
    return orderDate;
}

public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
}

public boolean isPaid() {
    return paid;
}

public void setPaid(boolean paid) {
    this.paid = paid;
}

public Integer getAmount() {
    return amount;
}

public void setAmount(Integer amount) {
    this.amount = amount;
}

public List<ArrayList> getProductList() {
    return productList;
}

public void setProductList(List<ArrayList> productList) {
    this.productList = productList;
}

public Float getPrice() {
    return price;
}

public void setPrice(Float price) {
    this.price = price;
}

}