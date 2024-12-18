package org.factoriaf5.p4_gijon_project_funkoshop_backend.product;

import java.math.BigDecimal;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.category.Category;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ProductDTOTest {

    public ProductDTOTest() {
    }

    @Test
    public void testGetId() {
        System.out.println("getId");
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        Long expResult = null;
        Long result = instance.getId();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetName() {
        System.out.println("getName");
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        String expResult = null;
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        String expResult = null;
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetPrice() {
        System.out.println("getPrice");
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        BigDecimal expResult = null;
        BigDecimal result = instance.getPrice();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetStock() {
        System.out.println("getStock");
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        Integer expResult = null;
        Integer result = instance.getStock();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetDiscount() {
        System.out.println("getDiscount");
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        Integer expResult = null;
        Integer result = instance.getDiscount();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetCategoryId() {
        System.out.println("getCategoryId");
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        Long expResult = null;
        Long result = instance.getCategoryId();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetId() {
        System.out.println("setId");
        Long id = null;
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        instance.setId(id);
    }

    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = null;
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        instance.setName(name);
    }

    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        String description = null;
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        instance.setDescription(description);
    }

    @Test
    public void testSetPrice() {
        System.out.println("setPrice");
        BigDecimal price = null;
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        instance.setPrice(price);
    }

    @Test
    public void testSetStock() {
        System.out.println("setStock");
        Integer stock = null;
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        instance.setStock(stock);
    }

    @Test
    public void testSetDiscount() {
        System.out.println("setDiscount");
        Integer discount = null;
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        instance.setDiscount(discount);
    }

    @Test
    public void testSetImageHash() {
        System.out.println("setImageHash");
        byte[] imageHash = null;
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
    }
     
    @Test
    public void testSetImageHash2() {
        System.out.println("setImageHash2");
        byte[] imageHash2 = null;
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
    }
    
    @Test
    public void testSetCategoryId() {
        System.out.println("setCategoryId");
        Long categoryId = null;
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        instance.setCategoryId(categoryId);
    }
}