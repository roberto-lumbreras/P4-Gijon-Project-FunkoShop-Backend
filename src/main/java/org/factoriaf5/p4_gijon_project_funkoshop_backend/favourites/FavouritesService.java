package org.factoriaf5.p4_gijon_project_funkoshop_backend.favourites;

import java.util.List;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.Product;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.ProductRepository;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.User;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FavouritesService {
    private final FavouritesRepository favouritesRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public FavouritesService(FavouritesRepository favouritesRepository, UserRepository userRepository,
            ProductRepository productRepository) {
        this.favouritesRepository = favouritesRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // Añadir un favorito
    public FavouritesDTO addFavourite(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        // Verificar si ya existe el favorito
        if (favouritesRepository.findByUserIdAndProductId(userId, productId).isPresent()) {
            throw new RuntimeException("Favorite already exists for this user and product");
        }
        Favourites favourite = new Favourites();
        favourite.setUser(user);
        favourite.setProduct(product);
        Favourites savedFav = favouritesRepository.save(favourite);
        return new FavouritesDTO(savedFav);
    }

    // Eliminar un favorito
    public void removeFavourite(Long favouriteId, Long userId) {
        Favourites favourite = favouritesRepository.findById(favouriteId)
                .orElseThrow(() -> new RuntimeException("Favourite not found with id: " + favouriteId));
        // Opcional: verificar que el favorito pertenece al usuario que quiere
        // eliminarlo
        if (!favourite.getUser().getId().equals(userId)) {
            throw new SecurityException("You are not authorized to remove this favourite");
        }
        favouritesRepository.delete(favourite);
    }

    // Listar favoritos de un usuario
    public List<FavouritesDTO> getUserFavourites(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return favouritesRepository.findByUserId(userId)
                .stream()
                .map(FavouritesDTO::new)
                .toList();
    }
}