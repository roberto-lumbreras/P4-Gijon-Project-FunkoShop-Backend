package org.factoriaf5.p4_gijon_project_funkoshop_backend.product;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.category.Category;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    void testDeleteProduct() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        productService.deleteProduct(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testDeleteProductNotFound() {
        Long productId = 1L;
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> productService.deleteProduct(productId));
    }

    @Test
    void testDeleteProductThrowsExceptionWhenProductNotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.deleteProduct(productId);
        });
        assertTrue(exception.getMessage().contains("Product not found with id"));
    }

    @Test
    void testImageDeletionService() {
    }

    @Test
    void testCreateProduct() throws IOException {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(BigDecimal.valueOf(10.00));
        productDTO.setStock(10);
        productDTO.setCategoryId(1L);
        BigDecimal discount = BigDecimal.valueOf(1);
        productDTO.setDiscount(discount.multiply(BigDecimal.valueOf(1)).intValue());

        MultipartFile image1 = mock(MultipartFile.class);
        when(image1.isEmpty()).thenReturn(false);
        when(image1.getOriginalFilename()).thenReturn("image1.jpg");

        MultipartFile image2 = mock(MultipartFile.class);
        when(image2.isEmpty()).thenReturn(false);
        when(image2.getOriginalFilename()).thenReturn("image2.jpg");

        Category category = new Category();
        category.setId(1L);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        ProductDTO savedProductDTO = productService.createProduct(productDTO, image1, image2);

        assertNotNull(savedProductDTO);
        assertEquals("Test Product", savedProductDTO.getName());
        assertEquals("Test Description", savedProductDTO.getDescription());
        assertEquals(BigDecimal.valueOf(10.00), savedProductDTO.getPrice());
        assertEquals(10, savedProductDTO.getStock());
        assertNull(savedProductDTO.getId());
        assertEquals(1, savedProductDTO.getDiscount().intValue());
        assertNotNull(savedProductDTO.getImgUrl());
        assertNotNull(savedProductDTO.getImgUrl2());
    }

    @Test
    void testCreateProductWithExistingProduct() throws IOException {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(BigDecimal.valueOf(10.99));
        productDTO.setStock(10);
        productDTO.setCategoryId(1L);
        BigDecimal discount = BigDecimal.valueOf(0.1);
        productDTO.setDiscount(discount.multiply(BigDecimal.valueOf(100)).intValue());

        MultipartFile image1 = mock(MultipartFile.class);
        when(image1.isEmpty()).thenReturn(false);
        when(image1.getOriginalFilename()).thenReturn("image1.jpg");

        MultipartFile image2 = mock(MultipartFile.class);
        when(image2.isEmpty()).thenReturn(false);
        when(image2.getOriginalFilename()).thenReturn("image2.jpg");

        Category category = new Category();
        category.setId(1L);
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Existing Product");
        existingProduct.setDescription("Existing Description");
        existingProduct.setPrice(BigDecimal.valueOf(5.99));
        existingProduct.setStock(5);
        existingProduct.setCategory(category);
        existingProduct.setImgUrl("existing-image.jpg");
        existingProduct.setImgUrl2("existing-image2.jpg");

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(existingProduct));
        ProductDTO savedProductDTO = productService.createProduct(productDTO, image1, image2);

        assertNotNull(savedProductDTO);
        assertEquals("Test Product", savedProductDTO.getName());
        assertEquals("Test Description", savedProductDTO.getDescription());
        assertEquals(BigDecimal.valueOf(10.99), savedProductDTO.getPrice());
        assertEquals(10, savedProductDTO.getStock());
        assertEquals(1L, savedProductDTO.getCategoryId());
        assertEquals(BigDecimal.valueOf(0.1), savedProductDTO.getDiscount());
        assertNotNull(savedProductDTO.getImgUrl());
        assertNotNull(savedProductDTO.getImgUrl2());
    }

    @Test
    void testCreateProductWithCategoryNotFound() {
    }


    @Test
    void testFetchProductByIdThrowsExceptionWhenProductNotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.fetchProductById(productId);
        });
        assertTrue(exception.getMessage().contains("Product not found with id"));
    }

    @Test
    void testApplyDiscountThrowsExceptionForInvalidDiscount() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setPrice(BigDecimal.valueOf(100));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        assertThrows(IllegalArgumentException.class, () -> {
            productService.applyDiscount(productId, -10); 
        });
        assertThrows(IllegalArgumentException.class, () -> {
            productService.applyDiscount(productId, 150);
        });
    }

    @Test
    void testUpdateStockThrowsExceptionForNegativeStock() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setStock(10);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        assertThrows(IllegalArgumentException.class, () -> {
            productService.updateStock(productId, -5); 
        });
    }
}