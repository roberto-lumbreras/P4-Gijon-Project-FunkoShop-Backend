package org.factoriaf5.p4_gijon_project_funkoshop_backend.configuration.jwtoken;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class CustomUserDetailsTest {

    private CustomUserDetails customUserDetails;
    private String email = "user@example.com";
    private String password = "password1234";
    private List<GrantedAuthority> authorities;

    @BeforeEach
    void setUp() {
        authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        customUserDetails = new CustomUserDetails(email, password, authorities);
    }

    @Test
    void testGetUsername() {
        assertEquals(email, customUserDetails.getUsername());
    }

    @Test
    void testGetEmail() {
        assertEquals(email, customUserDetails.getEmail());
    }

    @Test
    void testGetPassword() {
        assertEquals(password, customUserDetails.getPassword());
    }

    @Test
    void testGetAuthorities() {
        assertEquals(authorities, customUserDetails.getAuthorities());
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(customUserDetails.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(customUserDetails.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(customUserDetails.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(customUserDetails.isEnabled());
    }
}