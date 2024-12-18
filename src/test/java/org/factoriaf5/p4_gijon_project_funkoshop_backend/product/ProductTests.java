package org.factoriaf5.p4_gijon_project_funkoshop_backend.product;

import java.math.BigDecimal;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.category.Category;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ProductTests {

    @Test
    public void testGetId() {
        System.out.println("getId");
        Product instance = new Product();
        Long expResult = null;
        Long result = instance.getId();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetName() {
        System.out.println("getName");
        Product instance = new Product();
        String expResult = null;
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Product instance = new Product();
        String expResult = null;
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetPrice() {
        System.out.println("getPrice");
        Product instance = new Product();
        BigDecimal expResult = null;
        BigDecimal result = instance.getPrice();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetStock() {
        System.out.println("getStock");
        Product instance = new Product();
        Integer expResult = null;
        Integer result = instance.getStock();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetDiscount() {
        System.out.println("getDiscount");
        Product instance = new Product();
        Integer expResult = null;
        Integer result = instance.getDiscount();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetCategory() {
        System.out.println("getCategory");
        Product instance = new Product();
        Category expResult = null;
        Category result = instance.getCategory();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetId() {
        System.out.println("setId");
        Long id = null;
        Product instance = new Product();
        instance.setId(id);
    }

    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = null;
        Product instance = new Product();
        instance.setName(name);

    }

    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        String description = null;
        Product instance = new Product();
        instance.setDescription(description);

    }

    @Test
    public void testSetPrice() {
        System.out.println("setPrice");
        BigDecimal price = null;
        Product instance = new Product();
        instance.setPrice(price);
    }
    @Test
    public void testSetStock() {
        System.out.println("setStock");
        Integer stock = null;
        Product instance = new Product();
        instance.setStock(stock);
    }

    @Test
    public void testSetDiscount() {
        System.out.println("setDiscount");
        Integer discount = null;
        Product instance = new Product();
        instance.setDiscount(discount);
    }

    @Test
    public void testSetCategory() {
        System.out.println("setCategory");
        Category category = null;
        Product instance = new Product();
        instance.setCategory(category);
    }
}