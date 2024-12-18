package org.factoriaf5.p4_gijon_project_funkoshop_backend.configuration.jwtoken;

import static org.mockito.Mockito.*;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class AuthEntryPointJwtTest {

    private AuthEntryPointJwt authEntryPointJwt;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private AuthenticationException authException;
    @Mock
    private PrintWriter writer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authEntryPointJwt = new AuthEntryPointJwt();
    }

    @Test
    void testCommenceSetsUnauthorizedResponse() throws Exception {
        when(request.getServletPath()).thenReturn("/api/test");
        when(authException.getMessage()).thenReturn("Access denied");
        when(response.getWriter()).thenReturn(writer);
        authEntryPointJwt.commence(request, response, authException);
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Map<String, Object> expectedBody = new HashMap<>();
        expectedBody.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        expectedBody.put("error", "Unauthorized");
        expectedBody.put("message", "Access denied");
        expectedBody.put("path", "/api/test");
        ObjectMapper mapper = new ObjectMapper();
        String expectedJson = mapper.writeValueAsString(expectedBody);
        verify(writer).write(expectedJson);
    }
}