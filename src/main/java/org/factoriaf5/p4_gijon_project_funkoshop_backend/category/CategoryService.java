package org.factoriaf5.p4_gijon_project_funkoshop_backend.category;

import java.util.List;
import java.util.Locale.Category;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getCategories() {
        return categoryRepository.findAll().stream() .map(x -> new CategoryDTO(x)).toList();
    }

    public CategoryDTO setCategory (Long categoryId, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(categoryId).orElse(null);
        if (existingCategory != null) {
            existingCategory.setName(categoryDTO.getName());
            existingCategory.setImageHash(categoryDTO.getImageHash());
            existingCategory.setHighLights(categoryDTO.getHighlights());
            return new CategoryDTO(categoryRepository.save(existingCategory));
        } else {
            throw new RuntimeException("Category not found");
        }
    }




}
