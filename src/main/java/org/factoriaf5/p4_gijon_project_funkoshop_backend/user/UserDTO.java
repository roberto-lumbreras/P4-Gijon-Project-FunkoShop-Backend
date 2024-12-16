package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;

import java.util.List;

public class UserDTO {

    private Long id;
    private String email;
    private String password;
    private String role;
    private String JwToken;
    private List<Long> favorites;
    // private String firstName;
    // private String lastName;
    // private String firstAddress;
    // private String secondAddress;
    // private Boolean shippingAddress;
    // private Boolean suscribed;
    // private String phoneNumber;


    // public UserDTO(String firstName, String lastName, String firstAddress, String secondAddress,
    //         Boolean shippingAddress, Boolean suscribed, String phoneNumber) {
    //     this.firstName = firstName;
    //     this.lastName = lastName;
    //     this.firstAddress = firstAddress;
    //     this.secondAddress = secondAddress;
    //     this.shippingAddress = shippingAddress;
    //     this.suscribed = suscribed;
    //     this.phoneNumber = phoneNumber;
    // }

    public UserDTO(Long id, String email, String password, String role, String JwtToken, List<Long> favorites) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
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

    // public String getFirstName() {
    //     return firstName;
    // }

    // public String getLastName() {
    //     return lastName;
    // }

    // public String getFirstAddress() {
    //     return firstAddress;
    // }

    // public String getSecondAddress() {
    //     return secondAddress;
    // }

    // public Boolean getShippingAddress() {
    //     return shippingAddress;
    // }

    // public Boolean getSuscribed() {
    //     return suscribed;
    // }

    // public String getPhoneNumber() {
    //     return phoneNumber;
    // }

}
