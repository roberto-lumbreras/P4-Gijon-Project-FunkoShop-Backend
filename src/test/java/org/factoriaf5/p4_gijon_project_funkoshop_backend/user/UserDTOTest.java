package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    @Test
    void testConstructorAndGetters() {
        List<Long> favorites = new ArrayList<>();
        favorites.add(1L);
        favorites.add(2L);
        UserDTO userDTO = new UserDTO(1L, "test@example.com", "password123", "ROLE_USER", "token123", favorites);
        assertEquals(1L, userDTO.getId());
        assertEquals("test@example.com", userDTO.getEmail());
        assertEquals("password123", userDTO.getPassword());
        assertEquals("ROLE_USER", userDTO.getRole());
        assertEquals("token123", userDTO.getJwToken());
        assertEquals(favorites, userDTO.getFavorites());
    }

    @Test
    void testSetters() {
        UserDTO userDTO = new UserDTO(null, null, null, null, null, null);
        userDTO.setId(2L);
        userDTO.setEmail("new@example.com");
        userDTO.setPassword("newPassword");
        userDTO.setRole("ROLE_ADMIN");
        userDTO.setJwToken("newToken");
        List<Long> newFavorites = new ArrayList<>();
        newFavorites.add(3L);
        userDTO.setFavorites(newFavorites);
        assertEquals(2L, userDTO.getId());
        assertEquals("new@example.com", userDTO.getEmail());
        assertEquals("newPassword", userDTO.getPassword());
        assertEquals("ROLE_ADMIN", userDTO.getRole());
        assertEquals("newToken", userDTO.getJwToken());
        assertEquals(newFavorites, userDTO.getFavorites());
    }

    @Test
    void testEmptyFavorites() {
        UserDTO userDTO = new UserDTO(1L, "test@example.com", "password123", "ROLE_USER", "token123", new ArrayList<>());
        assertNotNull(userDTO.getFavorites());
        assertTrue(userDTO.getFavorites().isEmpty());
    }
}