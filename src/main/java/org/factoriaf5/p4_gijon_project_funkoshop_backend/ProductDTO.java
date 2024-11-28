package org.factoriaf5.p4_gijon_project_funkoshop_backend;

import java.math.BigDecimal;

public record ProductDTO(
    Long id,
    String name,
    String description,
    BigDecimal price,
    Integer stock,
    Integer discount,
    String imageHash,
    String imageHash2,
    String categoryId){}
