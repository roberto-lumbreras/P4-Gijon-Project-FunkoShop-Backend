package org.factoriaf5.p4_gijon_project_funkoshop_backend.favourites;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouritesRepository extends JpaRepository<Favourites, Long> {
    List<Favourites> findByUserId(Long userId);

    Optional<Favourites> findByUserIdAndProductId(Long userId, Long productId);
}