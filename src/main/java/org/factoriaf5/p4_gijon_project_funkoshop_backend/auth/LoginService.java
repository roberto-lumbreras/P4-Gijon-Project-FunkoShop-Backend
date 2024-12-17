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
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String jwt = jwtUtils.generateTokenFromEmail(user.getEmail());
        user.setJwToken(jwt);
        repository.save(user);
        return jwt;
    }

    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

}


