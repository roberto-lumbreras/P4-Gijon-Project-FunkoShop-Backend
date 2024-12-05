package org.factoriaf5.p4_gijon_project_funkoshop_backend.auth;

import javax.management.relation.Role;

public class AuthResponse {
    
    private String email;
    private String jwtToken;
    private Role role;

    public AuthResponse(String email, String jwtToken, Role role) {
        this.email = email;
        this.jwtToken = jwtToken;
        this.role = role;
    }

    public String getJwtToken() {
        return jwtToken;        
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}