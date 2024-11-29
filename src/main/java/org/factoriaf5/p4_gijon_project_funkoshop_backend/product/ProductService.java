package org.factoriaf5.p4_gijon_project_funkoshop_backend.product;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.category.Category;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.category.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductService() {
    }

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;

    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found by id " + id));
        productRepository.deleteById(id);

    }

    public ProductDTO createProduct(ProductDTO productDTO) {

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found by id " + productDTO.getCategoryId()
                + ". Status: " + HttpStatus.NOT_FOUND));

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setCategory(category);
        product.setDiscount(productDTO.getDiscount());
        product.setImageHash(productDTO.getImageHash());
        product.setImageHash2(productDTO.getImageHash2());

        Product savedProduct = productRepository.save(product);
        return new ProductDTO(savedProduct);

    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found by id " + id + ". Status: " + HttpStatus.NOT_FOUND));

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found by id " + productDTO.getCategoryId() + ". Status: " + HttpStatus.NOT_FOUND));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDiscount(productDTO.getDiscount());
        product.setImageHash(productDTO.getImageHash());
        product.setImageHash2(productDTO.getImageHash2());
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);
        return new ProductDTO(updatedProduct);
    }

    public ProductDTO fetchProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                "Product not found by id " + id + ". Status: " + HttpStatus.NOT_FOUND));
        return new ProductDTO(product);
    }
}
