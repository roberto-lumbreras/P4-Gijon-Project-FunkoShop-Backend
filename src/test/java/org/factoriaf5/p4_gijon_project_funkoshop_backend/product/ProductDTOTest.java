/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package org.factoriaf5.p4_gijon_project_funkoshop_backend.product;

import java.math.BigDecimal;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.category.Category;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author CODER F5 ASTURIAS
 */
public class ProductDTOTest {

    public ProductDTOTest() {
    }

    /**
     * Test of getId method, of class ProductDTO.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        Long expResult = null;
        Long result = instance.getId();
        assertEquals(expResult, result);

    }

    /**
     * Test of getName method, of class ProductDTO.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        String expResult = null;
        String result = instance.getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of getDescription method, of class ProductDTO.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        String expResult = null;
        String result = instance.getDescription();
        assertEquals(expResult, result);

    }

    /**
     * Test of getPrice method, of class ProductDTO.
     */
    @Test
    public void testGetPrice() {
        System.out.println("getPrice");
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        BigDecimal expResult = null;
        BigDecimal result = instance.getPrice();
        assertEquals(expResult, result);

    }

    /**
     * Test of getStock method, of class ProductDTO.
     */
    @Test
    public void testGetStock() {
        System.out.println("getStock");
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        Integer expResult = null;
        Integer result = instance.getStock();
        assertEquals(expResult, result);

    }

    /**
     * Test of getDiscount method, of class ProductDTO.
     */
    @Test
    public void testGetDiscount() {
        System.out.println("getDiscount");
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        Integer expResult = null;
        Integer result = instance.getDiscount();
        assertEquals(expResult, result);

    }

    /**
     * Test of getImageHash method, of class ProductDTO.
     */
    @Test
    public void testGetImageHash() {
        System.out.println("getImageHash");
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        String expResult = null;
        String result = instance.getImageHash();
        assertEquals(expResult, result);

    }

    /**
     * Test of getImageHash2 method, of class ProductDTO.
     */
    @Test
    public void testGetImageHash2() {
        System.out.println("getImageHash2");
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        String expResult = null;
        String result = instance.getImageHash2();
        assertEquals(expResult, result);

    }

    /**
     * Test of getCategoryId method, of class ProductDTO.
     */
    @Test
    public void testGetCategoryId() {
        System.out.println("getCategoryId");
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        Long expResult = null;
        Long result = instance.getCategoryId();
        assertEquals(expResult, result);

    }

    /**
     * Test of setId method, of class ProductDTO.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        Long id = null;
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        instance.setId(id);

    }

    /**
     * Test of setName method, of class ProductDTO.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = null;
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        instance.setName(name);

    }

    /**
     * Test of setDescription method, of class ProductDTO.
     */
    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        String description = null;
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        instance.setDescription(description);

    }

    /**
     * Test of setPrice method, of class ProductDTO.
     */
    @Test
    public void testSetPrice() {
        System.out.println("setPrice");
        BigDecimal price = null;
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        instance.setPrice(price);

    }

    /**
     * Test of setStock method, of class ProductDTO.
     */
    @Test
    public void testSetStock() {
        System.out.println("setStock");
        Integer stock = null;
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        instance.setStock(stock);

    }

    /**
     * Test of setDiscount method, of class ProductDTO.
     */
    @Test
    public void testSetDiscount() {
        System.out.println("setDiscount");
        Integer discount = null;
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        instance.setDiscount(discount);

    }

    /**
     * Test of setImageHash method, of class ProductDTO.
     */
    @Test
    public void testSetImageHash() {
        System.out.println("setImageHash");
        String imageHash = null;
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        instance.setImageHash(imageHash);

    }

    /**
     * Test of setImageHash2 method, of class ProductDTO.
     */
    @Test
    public void testSetImageHash2() {
        System.out.println("setImageHash2");
        String imageHash2 = null;
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        instance.setImageHash2(imageHash2);

    }

    /**
     * Test of setCategoryId method, of class ProductDTO.
     */
    @Test
    public void testSetCategoryId() {
        System.out.println("setCategoryId");
        Long categoryId = null;
        ProductDTO instance = new ProductDTO(new Product().withCategory(new Category()));
        instance.setCategoryId(categoryId);

    }

}