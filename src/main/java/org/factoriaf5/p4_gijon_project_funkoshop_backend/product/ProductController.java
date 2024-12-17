package org.factoriaf5.p4_gijon_project_funkoshop_backend.product;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(
            @ModelAttribute ProductDTO productDTO,
            @RequestParam(value = "image1") MultipartFile image1,
            @RequestParam(value = "image2") MultipartFile image2) {
        try {
            ProductDTO savedProduct = productService.createProduct(productDTO, image1, image2);
            return ResponseEntity.ok(savedProduct);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error saving product: " + e.getMessage());
        }
    }



   /*  @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") ProductDTO productDTO,
            @RequestPart(value = "image1", required = false) MultipartFile image1,
            @RequestPart(value = "image2", required = false) MultipartFile image2) throws Exception {

        ProductDTO updatedProduct = productService.updateProduct(id, productDTO, image1, image2);
        return ResponseEntity.ok(updatedProduct);
    }
    
    */
    @PatchMapping("/discount/{id}")
    public ResponseEntity<ProductDTO> applyDiscountToProduct(@PathVariable Long id, @RequestBody Integer discount) {
        ProductDTO updatedProduct = productService.applyDiscount(id, discount);
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
            @PathVariable String keyword,
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam String sort) {
        Page<ProductDTO> products = productService.fetchProductsByKeyword(keyword, page, size, sort);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/discounted")
    public ResponseEntity<List<ProductDTO>> fetchDiscountedProducts() {
        List<ProductDTO> products = productService.fetchDiscountedProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/new")
    public ResponseEntity<Page<ProductDTO>> fetchNewProducts(
        @PageableDefault(size = 8, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductDTO> products = productService.fetchNewProducts(pageable);
        return ResponseEntity.ok(products);

    }

    @PatchMapping("/stock/{id}")
    public ResponseEntity<ProductDTO> updateProductStock(
        @PathVariable Long id,
        @RequestParam int quantity) {
        ProductDTO updatedProduct = productService.updateStock(id, quantity);
        return ResponseEntity.ok(updatedProduct);
}

}
