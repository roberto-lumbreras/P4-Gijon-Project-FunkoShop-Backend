package org.factoriaf5.p4_gijon_project_funkoshop_backend.category;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCategories() {
        Category marvel = new Category();
        marvel.setName("Marvel");
        Category dc = new Category();
        dc.setName("DC");
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(marvel, dc));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(marvel));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(dc));
        when(categoryRepository.findById(3L)).thenReturn(Optional.empty());
        List<CategoryDTO> category = categoryService.getCategories();
        assertEquals(2, category.size());
        verify(categoryRepository).findAll();
    }

    @Test
    void testSetCategory() {
        Category existingCategory = new Category(1L, "Marvel", null, false);
        CategoryDTO categoryDTO = new CategoryDTO( );
        categoryDTO.setName("Marvel Classic");
        categoryDTO.setHighlights(true);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);
        CategoryDTO updatedCategoryDTO = categoryService.setCategory(categoryDTO, 1L);
        assertEquals("Marvel Classic", updatedCategoryDTO.getName());
        assertTrue(updatedCategoryDTO.isHighlights());
        verify(categoryRepository).save(existingCategory);
    }

    @Test
    void testSetCategoryNotFound() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("UpdatedCategory");
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.setCategory(categoryDTO, 1L);
        });
        assertEquals("Category not found", exception.getMessage());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(0)).save(any(Category.class));
    }
}