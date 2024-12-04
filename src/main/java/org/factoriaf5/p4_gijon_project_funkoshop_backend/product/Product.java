package org.factoriaf5.p4_gijon_project_funkoshop_backend.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.category.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Integer discount;
    private String imageHash;
    private String imageHash2;
    @ManyToOne
    @JoinColumn(name="category_id")
    private @With Category category;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
