package org.factoriaf5.p4_gijon_project_funkoshop_backend.favourites;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favourites")
public class FavouritesController {

    private final FavouritesService favouritesService;

    public FavouritesController(FavouritesService favouritesService) {
        this.favouritesService = favouritesService;
    }

    // Añadir un favorito
    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<FavouritesDTO> addFavourite(@PathVariable Long userId, @PathVariable Long productId) {
        FavouritesDTO favourite = favouritesService.addFavourite(userId, productId);
        return ResponseEntity.ok(favourite);
    }

    // Eliminar un favorito por su ID (y validando usuario)
    @DeleteMapping("/{favouriteId}/user/{userId}")
    public ResponseEntity<Void> removeFavourite(@PathVariable Long favouriteId, @PathVariable Long userId) {
        favouritesService.removeFavourite(favouriteId, userId);
        return ResponseEntity.noContent().build();
    }

    // Listar favoritos de un usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavouritesDTO>> getUserFavourites(@PathVariable Long userId) {
        List<FavouritesDTO> favourites = favouritesService.getUserFavourites(userId);
        return ResponseEntity.ok(favourites);
    }
}
