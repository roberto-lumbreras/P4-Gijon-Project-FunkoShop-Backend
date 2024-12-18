package org.factoriaf5.p4_gijon_project_funkoshop_backend.auth;

import static org.junit.jupiter.api.Assertions.*;
import javax.management.relation.Role;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AuthResponseTest {

    @Test
    public void testConstructorAndGetters() {
        Role mockRole = Mockito.mock(Role.class);
        AuthResponse authResponse = new AuthResponse("test@example.com", "jwt-token-123", mockRole);
        assertNotNull(authResponse);
        assertEquals("test@example.com", authResponse.getEmail());
        assertEquals("jwt-token-123", authResponse.getJwtToken());
        assertEquals(mockRole, authResponse.getRole());
    }

    @Test
    public void testSetters() {
        AuthResponse authResponse = new AuthResponse("", "", null);
        authResponse.setEmail("new@example.com");
        authResponse.setJwtToken("new-token");
        Role mockRole = Mockito.mock(Role.class);
        authResponse.setRole(mockRole);
        assertEquals("new@example.com", authResponse.getEmail());
        assertEquals("new-token", authResponse.getJwtToken());
        assertEquals(mockRole, authResponse.getRole());
    }
}