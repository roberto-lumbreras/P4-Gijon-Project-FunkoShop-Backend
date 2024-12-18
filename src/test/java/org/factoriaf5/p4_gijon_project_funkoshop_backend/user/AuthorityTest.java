package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthorityTest {

    @Test
    void testDefaultConstructor() {
        Authority authority = new Authority();
        assertNull(authority.getUsername());
        assertNull(authority.getAuthority());
    }

    @Test
    void testParameterizedConstructor() {
        String username = "testUser";
        Role role = Role.ROLE_ADMIN;
        Authority authority = new Authority(username, role);
        assertEquals(username, authority.getUsername());
        assertEquals(role, authority.getAuthority());
    }

    @Test
    void testSetUsername() {
        Authority authority = new Authority();
        String username = "newUser";
        authority.setUsername(username);
        assertEquals(username, authority.getUsername());
    }

    @Test
    void testSetAuthority() {
        Authority authority = new Authority();
        Role role = Role.ROLE_USER;
        authority.setAuthority(role);
        assertEquals(role, authority.getAuthority());
    }
}