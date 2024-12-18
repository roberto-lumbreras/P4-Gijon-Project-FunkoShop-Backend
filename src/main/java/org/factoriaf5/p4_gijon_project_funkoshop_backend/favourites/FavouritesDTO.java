package org.factoriaf5.p4_gijon_project_funkoshop_backend.favourites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouritesDTO {

    private Long id;
    private Long userId;
    private Long productId;
    private String productName;
    private String userEmail;
}