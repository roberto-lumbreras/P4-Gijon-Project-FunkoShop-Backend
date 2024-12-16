package org.factoriaf5.p4_gijon_project_funkoshop_backend.auth;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    

    @Mock
    private LoginService loginService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @Before("setup")
    public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }
    @Test
    public void testLoginExitoso() throws Exception {
    
        when(loginService.login(any(AuthRequest.class))).thenReturn("token-de-prueba");

        
        AuthRequest authRequest = new AuthRequest();
        
        authRequest.setPassword("password");
        String json = "\",\"password\":\"" + authRequest.getPassword() + "\"}";

        
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login exitoso"))
                .andExpect(jsonPath("$.token").value("token-de-prueba"));
    }

    @Test
    public void testLoginFallido() throws Exception {
        
        when(loginService.login(any(AuthRequest.class))).thenThrow(new RuntimeException("Error de login"));

    
        AuthRequest authRequest = new AuthRequest();
        
        authRequest.setPassword("password");
        String json =  "\",\"password\":\"" + authRequest.getPassword() + "\"}";

    
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Error de login"));
    }
}
