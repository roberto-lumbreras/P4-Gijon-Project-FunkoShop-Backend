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
    void testValidateJwtToken_InvalidToken() {
        String invalidToken = "invalidToken";

        boolean isValid = jwtUtils.validateJwtToken(invalidToken);
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