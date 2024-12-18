package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import java.time.LocalDate;
import java.util.List;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.details.DetailOrder;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.order.Order.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {

    private Long orderId;
    private Long userId;
    private LocalDate orderDate;
    private Boolean isPaid;
    private String payment;
    private Status status;
    private Integer productQuantity;
    private List<DetailOrder> productList;
    private Float totalAmount;

    public OrderDTO() {

    }

    public OrderDTO(Order order) {
        this.orderId = order.getOrderId();
        this.userId = order.getUser().getId();
        this.orderDate = order.getOrderDate();
        this.isPaid = order.getIsPaid();
        this.payment = order.getPayment();
        this.status = order.getStatus();
        this.totalAmount = order.getTotalAmount();
        this.productList = order.getProductList();
        this.productQuantity = order.getProductQuantity();
    }
}