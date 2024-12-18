package org.factoriaf5.p4_gijon_project_funkoshop_backend.configuration.jwtoken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import jakarta.servlet.http.HttpServletRequest;

class JwtUtilsTest {

    @InjectMocks
    private JwtUtils jwtUtils;
    @Mock
    private HttpServletRequest request;
    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetJwtFromHeader() {
        when(request.getHeader("Authorization")).thenReturn("Bearer someToken");

        String token = jwtUtils.getJwtFromHeader(request);
        assertEquals("someToken", token);
    }

    @Test
    void testGenerateTokenFromEmail() {
        String email = "user@example.com";
        String token = jwtUtils.generateTokenFromEmail(email);

        assertNotNull(token);
    }

    @Test
    void testGetEmailFromJwtToken() {
        String email = "user@example.com";
        String token = jwtUtils.generateTokenFromEmail(email);

        String extractedEmail = jwtUtils.getEmailFromJwtToken(token);
        assertEquals(email, extractedEmail);
    }

    @Test
    void testValidateJwtToken_ValidToken() {
        String token = jwtUtils.generateTokenFromEmail("user@example.com");

        boolean isValid = jwtUtils.validateJwtToken(token);
        assertTrue(isValid);
    }

    @Test
    void testValidateJwtToken_InvalidToken() {
        String invalidToken = "invalidToken";

        boolean isValid = jwtUtils.validateJwtToken(invalidToken);
        assertFalse(isValid);
    }

    @Test
    void testValidateJwtToken_ExpiredToken() {
        String expiredToken = jwtUtils.generateTokenFromEmail("user@example.com");

        boolean isValid = jwtUtils.validateJwtToken(expiredToken);
        assertFalse(isValid);
    }

    @Test
    void testValidateJwtToken_MalformedToken() {
        String malformedToken = "malformedToken";

        boolean isValid = jwtUtils.validateJwtToken(malformedToken);
        assertFalse(isValid);
    }

    @Test
    void testValidateJwtToken_UnsupportedToken() {
        String unsupportedToken = "unsupportedToken";

        boolean isValid = jwtUtils.validateJwtToken(unsupportedToken);
        assertFalse(isValid);
    }
}