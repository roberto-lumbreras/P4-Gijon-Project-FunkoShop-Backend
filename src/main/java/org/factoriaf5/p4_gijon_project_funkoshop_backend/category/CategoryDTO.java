package org.factoriaf5.p4_gijon_project_funkoshop_backend.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private String imageHash;
    private boolean highlights;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.imageHash = category.getImageHash();
        this.highlights = category.isHighlights();
    }
}