package org.factoriaf5.p4_gijon_project_funkoshop_backend.product;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testCreateProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Funko Pop");

        // Simular la respuesta esperada
        when(productService.createProduct(any(), any(), any())).thenReturn(productDTO);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("name", "Funko Pop")
                .param("image1", "image1.jpg")
                .param("image2", "image2.jpg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Funko Pop"));
        
        verify(productService, times(1)).createProduct(any(), any(), any());
    }

    @Test
    public void testApplyDiscountToProduct() throws Exception {
        Long productId = 1L;
        int discount = 20;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);
        productDTO.setDiscount(discount);

        when(productService.applyDiscount(productId, discount)).thenReturn(productDTO);

        mockMvc.perform(patch("/api/products/discount/{id}", productId)
                .param("discount", String.valueOf(discount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.discount").value(discount));
        
        verify(productService, times(1)).applyDiscount(productId, discount);
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Long productId = 1L;

        doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    public void testFetchProductById() throws Exception {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);
        productDTO.setName("Funko Pop");

        when(productService.fetchProductById(productId)).thenReturn(productDTO);

        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("Funko Pop"));

        verify(productService, times(1)).fetchProductById(productId);
    }

    @Test
    public void testFetchProducts() throws Exception {
        int page = 0;
        int size = 10;
        String sort = "name";
        
        Page<ProductDTO> productPage = mock(Page.class);
        when(productService.fetchProducts(page, size, sort)).thenReturn(productPage);

        mockMvc.perform(get("/api/products")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .param("sort", sort))
                .andExpect(status().isOk());

        verify(productService, times(1)).fetchProducts(page, size, sort);
    }
}
