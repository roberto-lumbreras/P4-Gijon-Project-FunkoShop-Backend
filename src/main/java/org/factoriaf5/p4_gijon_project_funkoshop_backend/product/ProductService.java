package org.factoriaf5.p4_gijon_project_funkoshop_backend.product;

import java.io.IOException;
import java.math.BigDecimal;
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
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id. Status: 404 NOT_FOUND " + id));
        productRepository.deleteById(id);
    }
    

    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile image1, MultipartFile image2) throws IOException {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id. Status: 404 NOT_FOUND " + productDTO.getCategoryId()));
    
        // Eliminamos 'product' y usamos directamente 'savedProduct'
        Product savedProduct = new Product();
        savedProduct.setName(productDTO.getName());
        savedProduct.setDescription(productDTO.getDescription());
        savedProduct.setPrice(productDTO.getPrice());
        savedProduct.setStock(productDTO.getStock());
        savedProduct.setCategory(category);
        savedProduct.setDiscount(productDTO.getDiscount());
        savedProduct.setImageHash(image1 != null ? image1.getBytes() : null);
        savedProduct.setImageHash2(image2 != null ? image2.getBytes() : null);
        savedProduct.setCreatedAt(LocalDateTime.now());
    
        // Guardamos el producto en la base de datos
        savedProduct = productRepository.save(savedProduct);
    
        // Devolvemos el DTO con el producto guardado
        return new ProductDTO(savedProduct);
    }
    

    public ProductDTO updateProduct(Long id, ProductDTO productDTO, MultipartFile image1, MultipartFile image2) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id " + productDTO.getCategoryId()));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDiscount(productDTO.getDiscount());
        product.setCategory(category);

        if (image1 != null) {
            product.setImageHash(image1.getBytes());
        }
        if (image2 != null) {
            product.setImageHash2(image2.getBytes());
        }

        Product updatedProduct = productRepository.save(product);
        return new ProductDTO(updatedProduct);
    }

    public ProductDTO fetchProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Product not found with id " + id + ". Status: " + HttpStatus.NOT_FOUND));
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

    public ProductDTO applyDiscount(Long id, Integer discount) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Product not found by id " + id + ". Status: " + HttpStatus.NOT_FOUND));
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("The discount must be between 0 and 100.");
        }
        BigDecimal discountAmount = product.getPrice().multiply(BigDecimal.valueOf(discount))
                .divide(BigDecimal.valueOf(100));
        BigDecimal newPrice = product.getPrice().subtract(discountAmount);
        product.setPrice(newPrice);
        product.setDiscount(discount);
        Product updatedProduct = productRepository.save(product);
        return new ProductDTO(updatedProduct);
    }
}
