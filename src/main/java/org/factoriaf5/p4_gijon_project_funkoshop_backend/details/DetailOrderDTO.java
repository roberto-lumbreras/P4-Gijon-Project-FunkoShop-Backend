package org.factoriaf5.p4_gijon_project_funkoshop_backend.details;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.order.Order;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.Product;

public class DetailOrderDTO {
    private Long detailId;
    private Product product;
    private Order order;
    private Integer productQuantity;
    private Double price;

    public DetailOrderDTO(DetailOrder detailOrder) {
        this.detailId = detailOrder.getDetailId();
        this.product = detailOrder.getProduct();
        this.order = detailOrder.getOrder();
        this.productQuantity = detailOrder.getProductQuantity();
        this.price = detailOrder.getPrice();
    }

    public DetailOrderDTO() {

    }

    public Long getDetailId() {
        return detailId;
    }

    public Product getProduct() {
        return product;
    }

    public Order getOrder() {
        return order;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public Double getPrice() {
        return price;
    }
}
