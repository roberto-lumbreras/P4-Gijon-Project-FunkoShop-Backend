package org.factoriaf5.p4_gijon_project_funkoshop_backend.category;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
public class CategoryTest {
    public CategoryTest() {
    }
    @Test
    public void testGetId() {
        System.out.println("getId");
        Category instance = new Category();
        Long expResult = null;
        Long result = instance.getId();
        assertEquals(expResult, result);
    }
    @Test
    public void testIsHighlights() {
        System.out.println("isHighlights");
        Category instance = new Category();
        boolean expResult = false;
        boolean result = instance.isHighlights();
        assertEquals(expResult, result);
    }
     @Test
    public void testCategoryDefaultConstructor() {
        
        Category defaultCategory = new Category();
         assertNull(defaultCategory.getName(), "El nombre por defecto debe ser null");
        assertNull(defaultCategory.getImageHash(), "El hash de la imagen por defecto debe ser null");
        assertFalse(defaultCategory.isHighlights(), "El valor por defecto de 'highlights' debe ser false");
    }

    @Test
    public void testCategoryEqualsAndHashCode() {
       
        Category category1 = new Category(null, "Funko Pop", "abc123hash", true);
        Category category2 = new Category(null, "Funko Pop", "abc123hash", true);

        assertEquals(category1, category2, "Las categorías con los mismos valores deben ser iguales");
        assertEquals(category1.hashCode(), category2.hashCode(), "El hashCode de las categorías iguales debe coincidir");
        Category category3 = new Category(null, "Other Category", "xyz456hash", false);
        assertNotEquals(category1, category3, "Las categorías con valores diferentes no deben ser iguales");
        assertNotEquals(category1.hashCode(), category3.hashCode(), "El hashCode de categorías diferentes debe ser diferente");
    }

    @Test
    public void testCategoryToString() {
        Category newCategory = new Category(null, "Funko Pop", "abc123hash", true);
        String expectedToString = "Category(id=null, name=Funko Pop, imageHash=abc123hash, highlights=true)";
        assertEquals(expectedToString, newCategory.toString(), "El método toString() debe devolver el formato esperado");
}
}