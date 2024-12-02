package org.factoriaf5.p4_gijon_project_funkoshop_backend.category;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CategoryService categoryService;
   
    
    @Test
    void testGetCategories() throws Exception {
        

        CategoryDTO category1 = new CategoryDTO();
        category1.setId(1L);
        category1.setName("Category 1");
        category1.setImageHash("hash1");
        category1.setHighlights(true);

        CategoryDTO category2 = new CategoryDTO();
        category2.setId(2L);
        category2.setName("Category 2");
        category2.setImageHash("hash2");
        category2.setHighlights(false);

        List<CategoryDTO> categories = Arrays.asList(category1, category2);

        
        when(categoryService.getCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Category 1"))
                .andExpect(jsonPath("$[0].imageHash").value("hash1"))
                .andExpect(jsonPath("$[0].highlights").value(true))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Category 2"))
                .andExpect(jsonPath("$[1].imageHash").value("hash2"))
                .andExpect(jsonPath("$[1].highlights").value(false));
    }
    

    @Test
    void testSetCategory() throws Exception {
        
        CategoryDTO inputCategory = new CategoryDTO();
        inputCategory.setName("Updated Category");
        inputCategory.setImageHash("updatedHash");
        inputCategory.setHighlights(true);

        CategoryDTO updatedCategory = new CategoryDTO();
        updatedCategory.setId(1L);
        updatedCategory.setName("Updated Category");
        updatedCategory.setImageHash("updatedHash");
        updatedCategory.setHighlights(true);

        
        when(categoryService.setCategory(any(CategoryDTO.class), eq(1L))).thenReturn(updatedCategory);


        mockMvc.perform(put("/api/categories/1")
        .contentType("application/json")
        .content(new ObjectMapper().writeValueAsString(inputCategory)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Updated Category"))
        .andExpect(jsonPath("$.imageHash").value("updatedHash"))
        .andExpect(jsonPath("$.highlights").value(true));
    }
}


