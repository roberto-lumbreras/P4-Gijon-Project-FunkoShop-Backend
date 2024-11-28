package main.java.org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @Column(name = "paid", nullable = false)
    private Boolean isPaid;

    @Column(name = "status", nullable = false)
    private Enum status;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "product_list", nullable = false)
    private List<ArrayList> productList;

    @Column(name = "price", nullable = false)
    private Float price;

    public Order(Long id, User user, Date orderDate, Boolean isPaid, Enum status, int amount,
            List<ArrayList> productList, Float price) {
        this.id = id;
        this.user = user;
        this.orderDate = orderDate;
        this.isPaid = isPaid;
        this.status = status;
        this.amount = amount;
        this.productList = productList;
        this.price = price;
    }

    public Order() {

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

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
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
