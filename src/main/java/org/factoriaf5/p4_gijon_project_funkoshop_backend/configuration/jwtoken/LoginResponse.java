package org.factoriaf5.p4_gijon_project_funkoshop_backend.configuration.jwtoken;

public class LoginResponse {
    
    private String jwtToken;
    private String email;
    private enum roles {
        USER, ADMIN
    };

    public LoginResponse(String jwtToken, String email) {
        this.jwtToken = jwtToken;
        this.email = email;
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