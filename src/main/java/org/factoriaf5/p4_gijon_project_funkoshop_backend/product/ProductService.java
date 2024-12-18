package org.factoriaf5.p4_gijon_project_funkoshop_backend.product;

import java.io.File;
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

    private void imageDeletionService(String imgUrl) {
        imgUrl = System.getProperty("user.dir") + "/src/main/resources/static" + imgUrl;
        File img = new File(imgUrl);
        if (img.exists() && !imgUrl.contains("default")) {
            img.delete();
        }
    }

    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile image1, MultipartFile image2)
            throws IOException {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException(
                        "Category not found with id. Status: 404 NOT_FOUND " + productDTO.getCategoryId()));
        Product savedProduct = new Product();
        savedProduct.setName(productDTO.getName());
        savedProduct.setDescription(productDTO.getDescription());
        savedProduct.setPrice(productDTO.getPrice());
        savedProduct.setStock(productDTO.getStock());
        savedProduct.setCategory(category);
        savedProduct.setDiscount(productDTO.getDiscount());
        if (!image1.isEmpty()) {
            if (savedProduct.getId() != null) {
                String url = fetchProductById(savedProduct.getId()).getImgUrl();
                if (url != null) {
                    imageDeletionService(url);
                }
            }
            savedProduct.setImgUrl(imageCreationService(image1));
        } else if (savedProduct.getId() != null) {
            savedProduct.setImgUrl(fetchProductById(savedProduct.getId()).getImgUrl());
        }
        if (!image2.isEmpty()) {
            if (savedProduct.getId() != null) {
                String url = fetchProductById(savedProduct.getId()).getImgUrl2();
                if (url != null) {
                    imageDeletionService(url);
                }
            }
            savedProduct.setImgUrl2(imageCreationService(image2));
        } else if (savedProduct.getId() != null) {
            savedProduct.setImgUrl2(fetchProductById(savedProduct.getId()).getImgUrl2());
        }
        savedProduct.setCreatedAt(LocalDateTime.now());
        savedProduct = productRepository.save(savedProduct);
        if (savedProduct == null) {
            throw new RuntimeException(
                    "Failed to save the product to the database. Status: 500 INTERNAL SERVER ERROR.");
        } else {
            return new ProductDTO(savedProduct);
        }
    }

    private String imageCreationService(MultipartFile img) throws IOException {
        final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/images";
        File uploadDir = new File(UPLOAD_DIR);
        System.out.println("ruta del directorio de carga de imagen -> " + uploadDir.getAbsolutePath());
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String filename = System.currentTimeMillis() + "_" + img.getOriginalFilename();
        uploadDir = new File(uploadDir, filename);
        System.out.println("ruta del archivo de imagen -> " + uploadDir.getAbsolutePath());
        img.transferTo(uploadDir);
        return "/images/" + filename;
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
        List<ProductDTO> filteredProducts = productPage.stream()
                .filter(x -> x.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        x.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .map(ProductDTO::new)
                .collect(Collectors.toList());
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