package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;

import java.util.List;

public class UserDTO {

    private Long id;
    private String email;
    private String password;
    private Role role;
    private String JwToken;
    private List<Long> favorites;
    /* private ProfileDto profile; */

    public UserDTO(Long id, String email, String password, Role role, String JwtToken, List<Long> favorites) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.JwToken = JwtToken;
        this.favorites = favorites;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getJwToken() {
        return JwToken;
    }

    public void setJwToken(String jwToken) {
        JwToken = jwToken;
    }

    public List<Long> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Long> favorites) {
        this.favorites = favorites;
    }

}
