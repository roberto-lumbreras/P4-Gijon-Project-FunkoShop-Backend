package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;

import java.util.List;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.Product;

import jakarta.persistence.Column;
/* import org.factoriaf5.p4_gijon_project_funkoshop_backend.profile.Profile; */
/*  */
//import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
//import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private Boolean enabled;
    private Role role;
    @Column(name = "jw_token")
    private String JwToken;

    @ManyToMany
    @JoinTable(name = "user_favorites", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> favorites;
    /*
     * @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
     * private Profile profile;
     */

}
