package org.factoriaf5.p4_gijon_project_funkoshop_backend.product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.category.Category;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

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
        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);
        return new ProductDTO(savedProduct);

    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Product not found by id " + id + ". Status: " + HttpStatus.NOT_FOUND));

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found by id " + productDTO.getCategoryId()
                        + ". Status: " + HttpStatus.NOT_FOUND));

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

    public Page<ProductDTO> fetchProductsByKeyword(String keyword, Integer page, Integer size, String sort) {
        String[] splitSort = sort.split(",");

        String sortField = splitSort[0];
        String sortDirection = splitSort[1].toUpperCase();

        Sort.Direction direction = Sort.Direction.valueOf(sortDirection);

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        Page<Product> productPage = productRepository.findAll(pageable);

        // Filtrar los productos después de obtener la página
        List<ProductDTO> filteredProducts = productPage.stream()
                .filter(x -> x.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        x.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .map(ProductDTO::new)
                .collect(Collectors.toList());

        // Convertir la lista filtrada a una página
        return new PageImpl<>(filteredProducts, pageable, productPage.getTotalElements());
    }

    public Page<ProductDTO> fetchProducts(Integer page, Integer size, String sort) {
        String[] splitSort = sort.split(",");

        String sortField = splitSort[0];
        String sortDirection = splitSort[1].toUpperCase();

        Sort.Direction direction = Sort.Direction.valueOf(sortDirection);

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        return productRepository.findAll(pageable).map(ProductDTO::new);
    }

    public Page<ProductDTO> fetchProductsByCategory(Long categoryId, Integer page, Integer size, String sort) {
        String[] splitSort = sort.split(",");
        String sortField = splitSort[0];
        String sortDirection = splitSort[1].toUpperCase();
        Sort s = Sort.by(Sort.Direction.valueOf(sortDirection.toUpperCase()), sortField);
        return productRepository.fetchProductsByCategory(categoryId, PageRequest.of(page, size, s))
                .map(ProductDTO::new);
    }

    public Page<ProductDTO> fetchNewProducts(Pageable pageable) {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        return productRepository.findByCreatedAtAfter(thirtyDaysAgo, pageable)
                .map(ProductDTO::new);
    }

    public List<ProductDTO> fetchDiscountedProducts() {
        List<ProductDTO> discountedProducts = productRepository.findAll()
                .stream()
                .filter(product -> product.getDiscount() > 0)
                .map(ProductDTO::new)
                .collect(Collectors.toList());

        return discountedProducts;

    }

    public ProductDTO updateStock(Long productId, int quantity) {
  
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found by id " + productId));
    
       
        if (quantity < 0) {
            throw new IllegalArgumentException("Stock cannot be negative. Current stock: " + product.getStock());
        }
    
  
        product.setStock(quantity);
  
        Product updatedProduct = productRepository.save(product);
    
        return new ProductDTO(updatedProduct);
    }
    
}