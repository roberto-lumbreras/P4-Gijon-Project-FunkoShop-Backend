package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface  Repository extends JpaRepository<User, Long> {
//User findByUsername(String username);
}
