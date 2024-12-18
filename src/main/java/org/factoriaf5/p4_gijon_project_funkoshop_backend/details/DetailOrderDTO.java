package org.factoriaf5.p4_gijon_project_funkoshop_backend.details;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailOrderDTO {
    private Long detailId;
    private Product product;
    private Integer productQuantity;
    private Double price;

    public DetailOrderDTO(DetailOrder detailOrder) {
        this.detailId = detailOrder.getId();
        this.product = detailOrder.getProduct();
        this.productQuantity = detailOrder.getQuantity();
        this.price = detailOrder.getPrice();
    }

    public DetailOrderDTO() {

    }
}