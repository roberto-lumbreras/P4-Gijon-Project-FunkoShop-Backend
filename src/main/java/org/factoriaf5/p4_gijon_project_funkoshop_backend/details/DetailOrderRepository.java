package org.factoriaf5.p4_gijon_project_funkoshop_backend.details;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailOrderRepository extends JpaRepository<DetailOrder, Long> {
    
}
