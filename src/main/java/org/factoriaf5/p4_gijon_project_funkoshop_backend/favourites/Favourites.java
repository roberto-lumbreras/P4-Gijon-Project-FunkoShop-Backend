package org.factoriaf5.p4_gijon_project_funkoshop_backend.favourites;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.Product;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "favorites")
public class Favourites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @ManyToOne //(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    
    
    @ManyToOne //(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;


    public Long getId() {
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }


    public FavouritesDTO getUser() {
        throw new UnsupportedOperationException("Unimplemented method 'getUser'");
    }


    public FavouritesDTO getProduct() {
        throw new UnsupportedOperationException("Unimplemented method 'getProduct'");
    }


}
