package org.factoriaf5.p4_gijon_project_funkoshop_backend.CATEGORY;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

    int countByHighlights(boolean highlights);

}


