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