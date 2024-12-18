package org.factoriaf5.p4_gijon_project_funkoshop_backend.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.configuration.jwtoken.JwtUtils;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.User;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

public class LoginServiceTest {

    private LoginService loginService;
    private JwtUtils jwtUtils;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        jwtUtils = Mockito.mock(JwtUtils.class);
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        loginService = new LoginService();
    }

    @Test
    void login_Success() {
        AuthRequest request = new AuthRequest();
        request.setEmail("test@example.com");
        request.setPassword("raw_password");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encoded_password");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        when(passwordEncoder.matches("raw_password", "encoded_password")).thenReturn(true);

        when(jwtUtils.generateTokenFromEmail("test@example.com")).thenReturn("jwt_token");

        String token = loginService.login(request);

        assertEquals("jwt_token", token);

        assertEquals("jwt_token", user.getJwToken());
        verify(userRepository).save(user);
    }

    @Test
    void login_UserNotFound() {
        AuthRequest request = new AuthRequest();
        request.setEmail("nonexistent@example.com");
        request.setPassword("password");

        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> loginService.login(request));
        assertEquals("User not found", exception.getMessage());

        verify(userRepository, never()).save(any());
    }

    @Test
    void login_InvalidPassword() {
        AuthRequest request = new AuthRequest();
        request.setEmail("test@example.com");
        request.setPassword("wrong_password");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encoded_password");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        when(passwordEncoder.matches("wrong_password", "encoded_password")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> loginService.login(request));
        assertEquals("Invalid password", exception.getMessage());

        verify(userRepository, never()).save(any());
    }
}