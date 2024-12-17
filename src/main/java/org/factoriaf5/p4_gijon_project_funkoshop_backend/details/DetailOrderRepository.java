package org.factoriaf5.p4_gijon_project_funkoshop_backend.details;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailOrderRepository extends JpaRepository <DetailOrder, Long>{

    List<DetailOrder> findByOrderId(Long orderId);
    
}
