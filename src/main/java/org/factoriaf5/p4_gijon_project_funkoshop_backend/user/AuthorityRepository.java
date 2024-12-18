package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    <Optional> Authority findByUsername(String username);
}
