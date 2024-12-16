package org.factoriaf5.p4_gijon_project_funkoshop_backend.configuration.jwtoken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.http.HttpServletRequest;


public class JwtUtilsTest {
    @InjectMocks
    private JwtUtils jwtUtils;

    @Mock
    private HttpServletRequest request;

    private String validToken;
    private String invalidToken;
    private String email = "testuser@example.com";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        validToken = jwtUtils.generateTokenFromEmail(email);
        invalidToken = "invalid.token";
    }
    
    @Test
    void testGenerateTokenFromEmail() {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validToken);
        
        String token = jwtUtils.getJwtFromHeader(request);
        
        assertNotNull(token);
        assertEquals(validToken, token);
    }

    @Test
    void testGetEmailFromJwtToken() {

    }

    @Test
    void testGetJwtFromHeader() {

    }

    @Test
    void testValidateJwtToken() {

    }
}
