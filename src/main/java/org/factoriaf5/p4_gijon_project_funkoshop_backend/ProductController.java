package org.factoriaf5.p4_gijon_project_funkoshop_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api-endpoint}/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.addProduct(productDTO);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> fetchProductById(@PathVariable Long id) {
        ProductDTO product = productService.fetchProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> fetchProducts(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam String sort) {
        Page<ProductDTO> products = productService.fetchProducts(page, size, sort);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<ProductDTO>> fetchProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam String sort) {
        Page<ProductDTO> products = productService.fetchProductsByCategory(categoryId, page, size, sort);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<Page<ProductDTO>> fetchProductsByKeyword(
            @PathVariable String keywordId,
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam String sort) {
        Page<ProductDTO> products = productService.fetchProductsByCategory(keywordId, page, size, sort);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/discounted")
    public ResponseEntity<Page<ProductDTO>> fetchDiscountedProducts() {
        Page<ProductDTO> products = productService.fetchDiscountedProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/new")
    public ResponseEntity<Page<ProductDTO>> fetchNewProducts() {
        Page<ProductDTO> products = productService.fetchNewProducts();
        return ResponseEntity.ok(products);
    }
}