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
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testDeleteProductNotFound() {
        // Arrange
        Long productId = 1L;
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act and Assert
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
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(BigDecimal.valueOf(10.00));
        productDTO.setStock(10);
        productDTO.setCategoryId(1L);
        BigDecimal discount = BigDecimal.valueOf(1);
        productDTO.setDiscount(discount.multiply(BigDecimal.valueOf(1)).intValue());
        //productDTO.setDiscount(discount);

        MultipartFile image1 = mock(MultipartFile.class);
        when(image1.isEmpty()).thenReturn(false);
        when(image1.getOriginalFilename()).thenReturn("image1.jpg");

        MultipartFile image2 = mock(MultipartFile.class);
        when(image2.isEmpty()).thenReturn(false);
        when(image2.getOriginalFilename()).thenReturn("image2.jpg");

        Category category = new Category();
        category.setId(1L);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        // Configuración del método productRepository.save()
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ProductDTO savedProductDTO = productService.createProduct(productDTO, image1, image2);

        // Assert
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
        // Arrange
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

        // Act
        ProductDTO savedProductDTO = productService.createProduct(productDTO, image1, image2);

        // Assert
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




 /*    @Test
    void testFetchProductById() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Sample Product");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductDTO result = productService.fetchProductById(productId);

        assertNotNull(result);
        assertEquals(product.getName(), result.getName());
    }
 */
    @Test
    void testFetchProductByIdThrowsExceptionWhenProductNotFound() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.fetchProductById(productId);
        });

        assertTrue(exception.getMessage().contains("Product not found with id"));
    }

    /* @Test
    void testApplyDiscount() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setPrice(BigDecimal.valueOf(100));
        product.setDiscount(0);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        int discount = 20; // 20%
        ProductDTO result = productService.applyDiscount(productId, discount);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(80), result.getPrice());
        assertEquals(discount, result.getDiscount());
    } */

    @Test
    void testApplyDiscountThrowsExceptionForInvalidDiscount() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setPrice(BigDecimal.valueOf(100));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(IllegalArgumentException.class, () -> {
            productService.applyDiscount(productId, -10); // Invalid discount
        });

        assertThrows(IllegalArgumentException.class, () -> {
            productService.applyDiscount(productId, 150); // Invalid discount
        });
    }

    /* @Test
    void testFetchProductsByKeyword() {
        String keyword = "sample";
        Product product1 = new Product();
        product1.setName("Sample Product 1");
        product1.setDescription("Description 1");

        Product product2 = new Product();
        product2.setName("Another Product");
        product2.setDescription("Sample Description 2");

        List<Product> productList = Arrays.asList(product1, product2);
        Page<Product> productPage = new PageImpl<>(productList);

        when(productRepository.findAll(any(PageRequest.class))).thenReturn(productPage);

        Page<ProductDTO> result = productService.fetchProductsByKeyword(keyword, 0, 10, "name,ASC");

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertTrue(result.getContent().get(0).getName().contains(keyword) ||
                   result.getContent().get(0).getDescription().contains(keyword));
    }

    @Test
    void testUpdateStock() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setStock(10);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        int newStock = 20;
        ProductDTO result = productService.updateStock(productId, newStock);

        assertNotNull(result);
        assertEquals(newStock, result.getStock());
    }
 */
    @Test
    void testUpdateStockThrowsExceptionForNegativeStock() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setStock(10);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(IllegalArgumentException.class, () -> {
            productService.updateStock(productId, -5); // Negative stock
        });
    }

}