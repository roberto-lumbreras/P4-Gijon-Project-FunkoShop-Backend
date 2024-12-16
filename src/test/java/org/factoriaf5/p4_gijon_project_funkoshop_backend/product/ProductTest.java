package org.factoriaf5.p4_gijon_project_funkoshop_backend.product;

import java.math.BigDecimal;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.category.Category;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author CODER F5 ASTURIAS
 */
public class ProductTest {

    /**
     * Test of getId method, of class Product.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Product instance = new Product();
        Long expResult = null;
        Long result = instance.getId();
        assertEquals(expResult, result);

    }

    /**
     * Test of getName method, of class Product.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Product instance = new Product();
        String expResult = null;
        String result = instance.getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of getDescription method, of class Product.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Product instance = new Product();
        String expResult = null;
        String result = instance.getDescription();
        assertEquals(expResult, result);

    }

    /**
     * Test of getPrice method, of class Product.
     */
    @Test
    public void testGetPrice() {
        System.out.println("getPrice");
        Product instance = new Product();
        BigDecimal expResult = null;
        BigDecimal result = instance.getPrice();
        assertEquals(expResult, result);

    }

    /**
     * Test of getStock method, of class Product.
     */
    @Test
    public void testGetStock() {
        System.out.println("getStock");
        Product instance = new Product();
        Integer expResult = null;
        Integer result = instance.getStock();
        assertEquals(expResult, result);

    }

    /**
     * Test of getDiscount method, of class Product.
     */
    @Test
    public void testGetDiscount() {
        System.out.println("getDiscount");
        Product instance = new Product();
        Integer expResult = null;
        Integer result = instance.getDiscount();
        assertEquals(expResult, result);

    }

    /**
     * Test of getImageHash method, of class Product.
     */
    @Test
    public void testGetImageHash() {
        System.out.println("getImageHash");
        Product instance = new Product();
        byte[] expResult = null;
        byte[] result = instance.getImageHash();
        assertEquals(expResult, result);

    }

    /**
     * Test of getImageHash2 method, of class Product.
     */
    @Test
    public void testGetImageHash2() {
        System.out.println("getImageHash2");
        Product instance = new Product();
        byte[] expResult = null;
        byte[] result = instance.getImageHash2();
        assertEquals(expResult, result);

    }

    /**
     * Test of getCategory method, of class Product.
     */
    @Test
    public void testGetCategory() {
        System.out.println("getCategory");
        Product instance = new Product();
        Category expResult = null;
        Category result = instance.getCategory();
        assertEquals(expResult, result);

    }

    /**
     * Test of setId method, of class Product.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        Long id = null;
        Product instance = new Product();
        instance.setId(id);

    }

    /**
     * Test of setName method, of class Product.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = null;
        Product instance = new Product();
        instance.setName(name);

    }

    /**
     * Test of setDescription method, of class Product.
     */
    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        String description = null;
        Product instance = new Product();
        instance.setDescription(description);

    }

    /**
     * Test of setPrice method, of class Product.
     */
    @Test
    public void testSetPrice() {
        System.out.println("setPrice");
        BigDecimal price = null;
        Product instance = new Product();
        instance.setPrice(price);

    }

    /**
     * Test of setStock method, of class Product.
     */
    @Test
    public void testSetStock() {
        System.out.println("setStock");
        Integer stock = null;
        Product instance = new Product();
        instance.setStock(stock);

    }

    /**
     * Test of setDiscount method, of class Product.
     */
    @Test
    public void testSetDiscount() {
        System.out.println("setDiscount");
        Integer discount = null;
        Product instance = new Product();
        instance.setDiscount(discount);

    }

    /**
     * Test of setImageHash method, of class Product.
     */
    @Test
    public void testSetImageHash() {
        System.out.println("setImageHash");
        byte[] imageHash = null;
        Product instance = new Product();
        instance.setImageHash(imageHash);

    }

    /**
     * Test of setImageHash2 method, of class Product.
     */
    @Test
    public void testSetImageHash2() {
        System.out.println("setImageHash2");
        byte[] imageHash2 = null;
        Product instance = new Product();
        instance.setImageHash2(imageHash2);

    }

    /**
     * Test of setCategory method, of class Product.
     */
    @Test
    public void testSetCategory() {
        System.out.println("setCategory");
        Category category = null;
        Product instance = new Product();
        instance.setCategory(category);

    }

}