package org.factoriaf5.p4_gijon_project_funkoshop_backend.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Integer discount;
    private String imgUrl;
    private String imgUrl2;
    private Long categoryId;
    private LocalDateTime createdAt;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.discount = product.getDiscount();
        this.imgUrl = product.getImgUrl();
        this.imgUrl2 = product.getImgUrl2();
        this.categoryId = product.getCategory().getId();
        this.createdAt = product.getCreatedAt();
    }
}