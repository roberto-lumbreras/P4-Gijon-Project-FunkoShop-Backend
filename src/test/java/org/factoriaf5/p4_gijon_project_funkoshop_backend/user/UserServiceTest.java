package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        // Inicializar los mocks
        MockitoAnnotations.openMocks(this);

        // Crear un objeto de usuario de prueba
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password123");
    }

    @Test
    void testGetUserById_success() {
        // Simular que el repositorio encuentra el usuario
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Llamada al servicio
        User result = userService.getUserById(1L);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    void testGetUserById_userNotFound() {
        // Simular que el usuario no se encuentra
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Llamada al servicio y comprobación de la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(1L);
        });
        assertEquals("User not found with ID: 1", exception.getMessage());
    }

    @Test
    void testAddUser_success() {
        // Simular que el email no está en uso
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedpassword123");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Llamada al servicio
        User result = userService.addUser(user);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals("encodedpassword123", result.getPassword());
    }

    @Test
    void testAddUser_emailAlreadyInUse() {
        // Simular que el email ya está en uso
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // Llamada al servicio y comprobación de la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.addUser(user);
        });
        assertEquals("Email already in use: test@example.com", exception.getMessage());
    }

   @Test
    void testDeleteUser_success() {
    // Simular que el usuario existe en el repositorio
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(userRepository.existsById(1L)).thenReturn(true); // Asegurarnos de que el usuario existe
    doNothing().when(userRepository).deleteById(1L); // Simulamos que el método deleteById no hace nada (que es lo esperado)

    // Llamada al servicio
    userService.deleteUser(1L);

    // Verificar que se llamó al método deleteById en el repositorio
    verify(userRepository, times(1)).deleteById(1L);
}

    @Test
    void testDeleteUser_userNotFound() {
        // Simular que el usuario no existe
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Llamada al servicio y comprobación de la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(1L);
        });
        assertEquals("User not found with ID: 1", exception.getMessage());
    }

    @Test
    void testChangePassword_success() {
        // Simular que el usuario existe
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("encodednewPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Llamada al servicio
        User result = userService.changePassword(1L, "newPassword");

        // Verificar el resultado
        assertNotNull(result);
        assertEquals("encodednewPassword", result.getPassword());
    }

    @Test
    void testChangePassword_userNotFound() {
        // Simular que el usuario no existe
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Llamada al servicio y comprobación de la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.changePassword(1L, "newPassword");
        });
        assertEquals("User not found with ID: 1", exception.getMessage());
    }
}
