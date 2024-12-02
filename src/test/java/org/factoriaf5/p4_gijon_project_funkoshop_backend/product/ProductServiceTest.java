package org.factoriaf5.p4_gijon_project_funkoshop_backend.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.category.Category;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.category.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    void testCreateProduct_WhenCategoryExists() {

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

        ProductDTO resulDto = productService.createProduct(productDTO);

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
        assertEquals("Product not found by id 1", exception.getMessage());
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
        assertEquals("Product not found by id 1. Status: 404 NOT_FOUND", exception.getMessage());
        verify(productRepository).findById(productId);

    }

    @Test
    void testUpdateProduct() {

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

        ProductDTO resultProductDTO = productService.updateProduct(productId, productDTO);

        assertNotNull(resultProductDTO);
        assertEquals("Night Fury Funko", resultProductDTO.getDescription());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_WhenProductDoesntExist(){
        Long productId = 1L;

        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(productId);
        productDTO.setDescription("Updated Funko");

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> productService.updateProduct(productId, productDTO));
        assertEquals("Product not found by id 1. Status: 404 NOT_FOUND", exception.getMessage());
        verify(productRepository).findById(productId);
        }

    @Test
    void testCreateProduct_WhenCategoryDoesntExists() {
        Long categoryId = 99L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(categoryId);
        productDTO.setName("Test Product");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> productService.createProduct(productDTO));
        assertEquals("Category not found by id 99. Status: 404 NOT_FOUND", exception.getMessage());
        verify(categoryRepository).findById(categoryId);
}

}
