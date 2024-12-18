package org.factoriaf5.p4_gijon_project_funkoshop_backend.auth;

public class AuthRequest {
    
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }    
    public void setPassword(String password) {
        this.password = password;
    }

}