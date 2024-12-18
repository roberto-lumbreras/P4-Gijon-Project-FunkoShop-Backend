package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.configuration.jwtoken.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private AuthorityRepository authorityRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder, jwtUtils, jdbcTemplate, authorityRepository);
    }

    @Test
    void testGetUserById_Success() {
        String token = "validToken";
        Long userId = 1L;
        String emailFromToken = "test@example.com";

        User mockUser = new User();
        mockUser.setEmail(emailFromToken);
        mockUser.setJwToken(token);
        
        when(jwtUtils.getEmailFromJwtToken(token)).thenReturn(emailFromToken);
        when(userRepository.findByEmail(emailFromToken)).thenReturn(Optional.of(mockUser));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        User result = userService.getUserById("Bearer " + token, userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

    @Test
    void testGetUserById_TokenMismatch() {
        String token = "validToken";
        Long userId = 1L;
        String emailFromToken = "test@example.com";

        User mockUser = new User();
        mockUser.setEmail(emailFromToken);
        mockUser.setJwToken("differentToken"); 
        
        when(jwtUtils.getEmailFromJwtToken(token)).thenReturn(emailFromToken);
        when(userRepository.findByEmail(emailFromToken)).thenReturn(Optional.of(mockUser));

        SecurityException exception = assertThrows(SecurityException.class, () -> {
            userService.getUserById("Bearer " + token, userId);
        });

        assertEquals("User request token doesn't match with user's database token", exception.getMessage());
    }

    @Test
    void testGetUsers_AccessDenied() {
        String token = "validToken";
        String emailFromToken = "test@example.com";
        
        User mockUser = new User();
        mockUser.setEmail(emailFromToken);
        mockUser.setJwToken(token);
        mockUser.setRole(Role.ROLE_USER); 
        
        when(jwtUtils.getEmailFromJwtToken(token)).thenReturn(emailFromToken);
        when(userRepository.findByEmail(emailFromToken)).thenReturn(Optional.of(mockUser));

        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () -> {
            userService.getUsers("Bearer " + token);
        });

        assertEquals("Access DENIED. User not authorized, only ADMIN", exception.getMessage());
    }

    @Test
    void testAddUser_Success() {
        User newUser = new User();
        newUser.setEmail("newuser@example.com");
        newUser.setPassword("password123");

        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("newuser@example.com");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        
        User result = userService.addUser(newUser);

        assertNotNull(result);
        assertEquals(newUser.getEmail(), result.getEmail());
    }

    @Test
    void testActiveUserById_Success() throws AccessDeniedException {
        String token = "validToken";
        Long userId = 1L;
        String emailFromToken = "admin@example.com";

        User mockAdmin = new User();
        mockAdmin.setEmail(emailFromToken);
        mockAdmin.setJwToken(token);
        mockAdmin.setRole(Role.ROLE_ADMIN);

        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setEnabled(false);

        when(jwtUtils.getEmailFromJwtToken(token)).thenReturn(emailFromToken);
        when(userRepository.findByEmail(emailFromToken)).thenReturn(Optional.of(mockAdmin));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        Boolean result = userService.activeUserById("Bearer " + token, userId);

        assertTrue(result);
        assertTrue(mockUser.getEnabled());
    }

    @Test
    void testDeleteUser_Success() throws AccessDeniedException {
        Long userId = 1L;

        User mockAdmin = new User();
        mockAdmin.setRole(Role.ROLE_ADMIN);
        mockAdmin.setJwToken("validToken");

        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setEmail("user@example.com");

        Authority mockAuthority = new Authority();
        mockAuthority.setUsername("user@example.com");

        when(jwtUtils.getEmailFromJwtToken("validToken")).thenReturn("admin@example.com");
        when(userRepository.findByEmail("admin@example.com")).thenReturn(Optional.of(mockAdmin));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(authorityRepository.findByUsername("user@example.com")).thenReturn(mockAuthority);

        userService.deleteUser("Bearer validToken", userId);

        verify(userRepository, times(1)).deleteById(userId);
        verify(authorityRepository, times(1)).delete(mockAuthority);
    }

    @Test
    void testChangePassword_Success() {
        Long userId = 1L;
        String newPassword = "newPassword123";
        String token = "validToken";
        String emailFromToken = "test@example.com";

        User mockUser = new User();
        mockUser.setEmail(emailFromToken);
        mockUser.setJwToken(token);

        when(jwtUtils.getEmailFromJwtToken(token)).thenReturn(emailFromToken);
        when(userRepository.findByEmail(emailFromToken)).thenReturn(Optional.of(mockUser));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setPassword("encodedNewPassword");

        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.changePassword("Bearer " + token, userId, newPassword);

        assertNotNull(result);
        assertEquals("encodedNewPassword", result.getPassword());
    }
}