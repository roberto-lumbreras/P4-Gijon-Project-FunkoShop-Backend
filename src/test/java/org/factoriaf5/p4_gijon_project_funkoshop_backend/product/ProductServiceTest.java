package org.factoriaf5.p4_gijon_project_funkoshop_backend.product;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.category.Category;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.category.CategoryRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct_WhenCategoryExists() throws IOException {

        Category category = new Category();
        category.setId(1L);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(1L);
        productDTO.setName("Test of Product");

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Test of Product");
        savedProduct.setCategory(category);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductDTO resulDto = productService.createProduct(productDTO, null, null);

        assertNotNull(resulDto);
        assertEquals("Test of Product", resulDto.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testDeleteProduct_WhenProductExiste() {

        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.deleteProduct(productId);

        verify(productRepository).deleteById(productId);
    }

    @Test
    void testDeleteProduct_WhenProductDoesntExiste() {

        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.deleteProduct(productId));
        assertEquals("Product not found with id. Status: 404 NOT_FOUND 1", exception.getMessage());
    }

    @Test
    void testFetchProductById_WhenProductExists() {
        Long productId =1L;

        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");
       


        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setCategory(category);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductDTO resultProductDTO = productService.fetchProductById(productId);
        assertNotNull(resultProductDTO);
        assertEquals("Test Product", resultProductDTO.getName());
        verify(productRepository).findById(productId);

    }

    @Test
    void testFetchProductById_WhenProductDoesntExists(){

        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        RuntimeException exception =assertThrows(RuntimeException.class,
            () -> productService.fetchProductById(productId));
        assertEquals("Product not found with id 1. Status: 404 NOT_FOUND", exception.getMessage());
        verify(productRepository).findById(productId);

    }

    @Test
    void testUpdateProduct() throws IOException {

        Long productId = 1L;

        Category category = new Category();
        category.setId(productId);

        Product existingProduct = new Product();
        existingProduct.setId(productId);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(productId);
        productDTO.setDescription("Toothless Funko");

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setDescription("Night Fury Funko");
        updatedProduct.setCategory(category);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findById(productId)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        ProductDTO resultProductDTO = productService.updateProduct(productId, productDTO, null, null);

        assertNotNull(resultProductDTO);
        assertEquals("Night Fury Funko", resultProductDTO.getDescription());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_WhenProductDoesntExist()throws Exception {
        Long productId = 1L;

        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(productId);
        productDTO.setDescription("Updated Funko");

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> productService.updateProduct(productId, productDTO, null, null));
        assertEquals("Product not found with id 1", exception.getMessage());
        verify(productRepository).findById(productId);
        }

    @Test
    void testCreateProduct_WhenCategoryDoesntExists() throws Exception {
        Long categoryId = 99L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(categoryId);
        productDTO.setName("Test Product");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> productService.createProduct(productDTO, null, null));
        assertEquals("Category not found with id. Status: 404 NOT_FOUND 99", exception.getMessage());
        verify(categoryRepository).findById(categoryId);
}

    @Test
    void testFetchDiscountedProducts() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Funkoshop Exclusive");
        product.setDescription("Special edition Funko Pop");
        product.setPrice(new BigDecimal("29.99"));
        product.setStock(50);
        product.setDiscount(10);
        product.setImageHash("imageHash1".getBytes());
        product.setImageHash2("imageHash2".getBytes());
        product.setCategory(new Category(1L, "Marvel", "", true));
        product.setCreatedAt(LocalDateTime.now());

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Funkoshop Exclusive2");
        product2.setDescription("Special edition Funko Pop2");
        product2.setPrice(new BigDecimal("29.99"));
        product2.setStock(50);
        product2.setDiscount(0);
        product2.setImageHash("imageHash21".getBytes());
        product2.setImageHash2("imageHash22".getBytes());
        product2.setCategory(new Category(1L, "Marvel2", "", true));
        product2.setCreatedAt(LocalDateTime.now());

        when(productRepository.findAll()).thenReturn(Arrays.asList(product, product2));

        List<ProductDTO> products = productService.fetchDiscountedProducts();
        assertEquals(1, products.size());
        verify(productRepository).findAll();
    }

    @Test
    void testFetchNewProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("New Product 1");
        product1.setCategory(new Category(1L, "name", "", true));
        product1.setCreatedAt(LocalDateTime.now().minusDays(5));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("New Product 2");
        product2.setCategory(new Category(1L, "name", "", true));
        product2.setCreatedAt(LocalDateTime.now().minusDays(40));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(Arrays.asList(product1), pageable, 1);

        when(productRepository.findByCreatedAtAfter(any(LocalDateTime.class), eq(pageable)))
            .thenReturn(productPage);

        Page<ProductDTO> result = productService.fetchNewProducts(pageable);

        assertNotNull(result);

        assertEquals(1, result.getContent().size());

        assertEquals("New Product 1", result.getContent().get(0).getName());

        verify(productRepository).findByCreatedAtAfter(any(LocalDateTime.class), eq(pageable));
    }




    @Test
    void testFetchProducts() {
        Product product = new Product();
        product.setId(1L);
        product.setName("New Product 1");
        product.setCategory(new Category(1L, "name", "", true));
        product.setCreatedAt(LocalDateTime.now().minusDays(5));
        String sort = "name,asc";
        Integer page = 0;
        Integer size = 1;
        Pageable pageable = PageRequest.of(page, size,Sort.by("name").ascending());
        Page<Product> expected = new PageImpl<>(Arrays.asList(product), pageable, 1);
        when(productRepository.findAll(any(Pageable.class))).thenReturn(expected);
        Page<ProductDTO> result = productService.fetchProducts(page, size, sort);
        assertEquals(expected.map(ProductDTO::new).getContent(),result.getContent());
    }

    @Test
    void testFetchProductsByCategory() {
        
    }

    @Test
    void testFetchProductsByKeyword() {
        
    }

}
