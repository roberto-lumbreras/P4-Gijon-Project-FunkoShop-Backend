package org.factoriaf5.p4_gijon_project_funkoshop_backend.product;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM PRODUCTS WHERE category_id = :category_id", nativeQuery = true)
    public Page<Product> fetchProductsByCategory(@Param("category_id") Long categoryId, Pageable pageable);

    Page<Product> findByCreatedAtAfter(LocalDateTime date, Pageable pageable);
}