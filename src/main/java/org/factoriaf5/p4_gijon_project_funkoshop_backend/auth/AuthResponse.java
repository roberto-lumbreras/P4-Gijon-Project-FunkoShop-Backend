package org.factoriaf5.p4_gijon_project_funkoshop_backend.auth;

public class AuthResponse {
    
    private String email;
    private String jwtToken;
    private enum roles {
        USER, ADMIN
    };

    public AuthResponse(String email, String jwtToken) {
        this.email = email;
        this.jwtToken = jwtToken;
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

}