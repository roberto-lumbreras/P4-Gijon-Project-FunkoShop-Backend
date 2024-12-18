package org.factoriaf5.p4_gijon_project_funkoshop_backend.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProduct_ShouldReturnCreatedProduct() throws Exception {
    ProductDTO productDTO = new ProductDTO();
    productDTO.setName("Test Product");
    productDTO.setDescription("Test Description");
    productDTO.setPrice(new BigDecimal("19.99"));
    productDTO.setStock(10);
    productDTO.setCategoryId(1L);

    MockMultipartFile file1 = new MockMultipartFile("file1", "test1.txt", "text/plain", "file1 content".getBytes());
    MockMultipartFile file2 = new MockMultipartFile("file2", "test2.txt", "text/plain", "file2 content".getBytes());

    when(productService.createProduct(any(ProductDTO.class), any(MultipartFile.class), any(MultipartFile.class)))
            .thenReturn(productDTO);

    mockMvc.perform(multipart("/api/products")
            .file(file1)
            .file(file2)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .param("name", productDTO.getName())
            .param("description", productDTO.getDescription())
            .param("price", productDTO.getPrice().toString())
            .param("stock", String.valueOf(productDTO.getStock()))
            .param("categoryId", String.valueOf(productDTO.getCategoryId())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Test Product"));

    verify(productService, times(1)).createProduct(any(ProductDTO.class), any(MultipartFile.class), any(MultipartFile.class));
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

    MockMultipartFile file1 = new MockMultipartFile("file1", "update1.txt", "text/plain", "file1 content".getBytes());
    MockMultipartFile file2 = new MockMultipartFile("file2", "update2.txt", "text/plain", "file2 content".getBytes());

    when(productService.updateProduct(eq(id), any(ProductDTO.class), any(MultipartFile.class), any(MultipartFile.class)))
            .thenReturn(productDTO);

    mockMvc.perform(multipart("/api/products/{id}", id)
            .file(file1)
            .file(file2)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .param("name", productDTO.getName())
            .param("description", productDTO.getDescription())
            .param("price", productDTO.getPrice().toString())
            .param("stock", String.valueOf(productDTO.getStock()))
            .param("categoryId", String.valueOf(productDTO.getCategoryId())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Updated Product"));

    verify(productService, times(1)).updateProduct(eq(id), any(ProductDTO.class), any(MultipartFile.class), any(MultipartFile.class));
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
void fetchProductsByCategory_ShouldReturnPagedProducts() throws Exception {
    Long categoryId = 1L;
    int page = 0, size = 10;
    String sort = "price,desc";

    Page<ProductDTO> productPage = new PageImpl<>(List.of(new ProductDTO()));
    when(productService.fetchProductsByCategory(categoryId, page, size, sort)).thenReturn(productPage);

    mockMvc.perform(get("/api/products/category/{categoryId}", categoryId)
            .param("page", String.valueOf(page))
            .param("size", String.valueOf(size))
            .param("sort", sort))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray());

    verify(productService, times(1)).fetchProductsByCategory(categoryId, page, size, sort);
}

@Test
void fetchProducts_ShouldReturnPagedProducts() throws Exception {
    int page = 0;
    int size = 10;
    String sort = "name,asc";

    Page<ProductDTO> productPage = new PageImpl<>(List.of(new ProductDTO()));
    when(productService.fetchProducts(page, size, sort)).thenReturn(productPage);

    mockMvc.perform(get("/api/products")
            .param("page", String.valueOf(page))
            .param("size", String.valueOf(size))
            .param("sort", sort))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray());

    verify(productService, times(1)).fetchProducts(page, size, sort);
}

@Test
void fetchProductsByKeyword_ShouldReturnPagedProducts() throws Exception {
    String keyword = "test";
    int page = 0, size = 10;
    String sort = "name,asc";

    Page<ProductDTO> productPage = new PageImpl<>(List.of(new ProductDTO()));
    when(productService.fetchProductsByKeyword(keyword, page, size, sort)).thenReturn(productPage);

    mockMvc.perform(get("/api/products/keyword/{keyword}", keyword)
            .param("page", String.valueOf(page))
            .param("size", String.valueOf(size))
            .param("sort", sort))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray());

    verify(productService, times(1)).fetchProductsByKeyword(keyword, page, size, sort);
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

@Test
void fetchNewProducts_ShouldReturnPagedNewProducts() throws Exception {
    Pageable pageable = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "createdAt"));
    Page<ProductDTO> newProducts = new PageImpl<>(List.of(new ProductDTO()));
    when(productService.fetchNewProducts(pageable)).thenReturn(newProducts);

    mockMvc.perform(get("/api/products/new"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray());

    verify(productService, times(1)).fetchNewProducts(any(Pageable.class));
}

}