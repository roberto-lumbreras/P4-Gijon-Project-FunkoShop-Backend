package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  UserRepository extends JpaRepository<User, Long> {

Optional<User> findByEmail(String email);
}
