package org.factoriaf5.p4_gijon_project_funkoshop_backend.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;

class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void addProduct_ShouldReturnCreatedProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(new BigDecimal("19.99"));
        productDTO.setStock(10);
        productDTO.setCategoryId(1L);

        when(productService.createProduct(any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));

        verify(productService, times(1)).createProduct(any(ProductDTO.class));
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
        Long id = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Updated Product");
        productDTO.setDescription("Updated Description");
        productDTO.setPrice(new BigDecimal("29.99"));
        productDTO.setStock(20);
        productDTO.setCategoryId(1L);

        when(productService.updateProduct(eq(id), any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(put("/api/products/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"));

        verify(productService, times(1)).updateProduct(eq(id), any(ProductDTO.class));
    }

    @Test
    void deleteProduct_ShouldReturnNoContent() throws Exception {
        Long id = 1L;

        doNothing().when(productService).deleteProduct(id);

        mockMvc.perform(delete("/api/products/{id}", id))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(id);
    }

    @Test
    void fetchProductById_ShouldReturnProduct() throws Exception {
        Long id = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        productDTO.setName("Fetched Product");

        when(productService.fetchProductById(id)).thenReturn(productDTO);

        mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fetched Product"));

        verify(productService, times(1)).fetchProductById(id);

    }

@Test
void fetchDiscountedProducts_ShouldReturnListOfDiscountedProducts() throws Exception {
    List<ProductDTO> discountedProducts = List.of(new ProductDTO());
    when(productService.fetchDiscountedProducts()).thenReturn(discountedProducts);

    mockMvc.perform(get("/api/products/discounted"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());

    verify(productService, times(1)).fetchDiscountedProducts();
}

}