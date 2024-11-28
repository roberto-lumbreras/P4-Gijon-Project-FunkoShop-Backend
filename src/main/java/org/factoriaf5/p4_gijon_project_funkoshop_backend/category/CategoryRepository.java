package org.factoriaf5.p4_gijon_project_funkoshop_backend.category;

import java.util.Locale.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
