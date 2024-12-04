package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;
/* import org.factoriaf5.p4_gijon_project_funkoshop_backend.profile.Profile; */
/*  */
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
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
    private String JwToken;
    /* @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile; */
 /*    public Object getUsername() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
    } */

}
