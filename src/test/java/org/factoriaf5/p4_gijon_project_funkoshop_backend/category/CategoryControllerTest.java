package org.factoriaf5.p4_gijon_project_funkoshop_backend.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private CategoryDTO category1;
    private CategoryDTO category2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configuración de datos de prueba
        category1 = new CategoryDTO();
        category1.setId(1L);
        category1.setName("Category 1");

        category2 = new CategoryDTO();
        category2.setId(2L);
        category2.setName("Category 2");
    }

    @Test
    void testGetCategories() {
        // Simular el comportamiento del servicio
        List<CategoryDTO> categories = Arrays.asList(category1, category2);
        when(categoryService.getCategories()).thenReturn(categories);

        // Llamar al método del controlador
        ResponseEntity<List<CategoryDTO>> response = categoryController.getCategories();

        // Verificar la respuesta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Category 1", response.getBody().get(0).getName());
        assertEquals("Category 2", response.getBody().get(1).getName());

        // Verificar que el servicio fue llamado
        verify(categoryService, times(1)).getCategories();
    }

    @Test
    void testSetCategory() {
        // Simular el comportamiento del servicio
        CategoryDTO updatedCategory = new CategoryDTO();
        updatedCategory.setId(1L);
        updatedCategory.setName("Updated Category");

        when(categoryService.setCategory(any(CategoryDTO.class), eq(1L))).thenReturn(updatedCategory);

        // Llamar al método del controlador
        ResponseEntity<CategoryDTO> response = categoryController.setCategory(category1, 1L);

        // Verificar la respuesta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Category", response.getBody().getName());
        assertEquals(1L, response.getBody().getId());

        // Verificar que el servicio fue llamado con los parámetros correctos
        verify(categoryService, times(1)).setCategory(category1, 1L);
    }
}
