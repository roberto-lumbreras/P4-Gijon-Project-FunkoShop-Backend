package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.Product;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testDefaultConstructor() {
        User user = new User();
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getRole());
        assertNull(user.getJwToken());
        assertNull(user.getFavorites());
        assertNull(user.getEnabled());
    }

    @Test
    void testParameterizedConstructor() {
        List<Product> favorites = new ArrayList<>();
        User user = new User(1L, "testUser", "test@example.com", "password123", true, Role.ROLE_USER, "token123", favorites);
        assertEquals(1L, user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertTrue(user.getEnabled());
        assertEquals(Role.ROLE_USER, user.getRole());
        assertEquals("token123", user.getJwToken());
        assertEquals(favorites, user.getFavorites());
    }

    @Test
    void testSetAndGetUsername() {
        User user = new User();
        user.setUsername("newUser");
        assertEquals("newUser", user.getUsername());
    }

    @Test
    void testSetAndGetEmail() {
        User user = new User();
        user.setEmail("new@example.com");
        assertEquals("new@example.com", user.getEmail());
    }

    @Test
    void testSetAndGetPassword() {
        User user = new User();
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    void testSetAndGetRole() {
        User user = new User();
        user.setRole(Role.ROLE_ADMIN);
        assertEquals(Role.ROLE_ADMIN, user.getRole());
    }

    @Test
    void testSetAndGetJwToken() {
        User user = new User();
        user.setJwToken("newToken");
        assertEquals("newToken", user.getJwToken());
    }

    @Test
    void testSetAndGetFavorites() {
        User user = new User();
        List<Product> favorites = new ArrayList<>();
        user.setFavorites(favorites);
        assertEquals(favorites, user.getFavorites());
    }

    @Test
    void testSetAndGetEnabled() {
        User user = new User();
        user.setEnabled(true);
        assertTrue(user.getEnabled());
    }
}