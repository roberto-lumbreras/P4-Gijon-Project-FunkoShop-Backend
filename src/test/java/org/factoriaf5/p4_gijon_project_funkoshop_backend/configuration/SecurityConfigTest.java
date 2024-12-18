package org.factoriaf5.p4_gijon_project_funkoshop_backend.configuration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import javax.sql.DataSource;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.configuration.jwtoken.AuthEntryPointJwt;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.configuration.jwtoken.AuthTokenFilter;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.AuthorityRepository;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

class SecurityConfigTest {

    @InjectMocks
    private SecurityConfig securityConfig;
    @Mock
    private DataSource dataSource;
    @Mock
    private AuthEntryPointJwt unauthorizedHandler;
    @Mock
    private AuthorityRepository authorityRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticationJwtTokenFilter() {
        AuthTokenFilter filter = securityConfig.authenticationJwtTokenFilter();
        assertNotNull(filter, "AuthTokenFilter should not be null");
    }

    @Test
    void testPasswordEncoder() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();
        assertNotNull(encoder, "PasswordEncoder should not be null");
    }

    @Test
    void testInitData() {
        when(userRepository.findByEmail("admin@email.com")).thenReturn(java.util.Optional.empty());
        when(passwordEncoder.encode("password1234")).thenReturn("encodedPassword");
        CommandLineRunner initData = securityConfig.initData(userRepository, passwordEncoder, authorityRepository);
        assertDoesNotThrow(() -> initData.run());
        verify(userRepository, times(1)).save(any());
        verify(authorityRepository, times(1)).save(any());
    }

    @Test
    void testAuthenticationManager() throws Exception {
        AuthenticationConfiguration authenticationConfiguration = mock(AuthenticationConfiguration.class);
        AuthenticationManager manager = mock(AuthenticationManager.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(manager);
        AuthenticationManager authManager = securityConfig.authenticationManager(authenticationConfiguration);
        assertNotNull(authManager, "AuthenticationManager should not be null");
    }
}