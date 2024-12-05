package org.factoriaf5.p4_gijon_project_funkoshop_backend.auth;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.configuration.jwtoken.JwtUtils;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.User;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public String login(AuthRequest request) {
        //busca el email en la base de datos y comprueba que existe
        User user = repository.findByEmail(request.getEmail()).orElseThrow(()-> new RuntimeException("User not found"));
        //comprueba la contraseña
        if(!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        //genera el token
        String jwt = jwtUtils.generateTokenFromEmail(user.getEmail());
        //mete el token en la base de datos 
        user.setJwToken(jwt);
        repository.save(user);
        //retorna el token
        return jwt;
    }

}
