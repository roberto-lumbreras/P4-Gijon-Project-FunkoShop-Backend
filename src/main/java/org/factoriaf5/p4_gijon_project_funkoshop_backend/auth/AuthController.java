package org.factoriaf5.p4_gijon_project_funkoshop_backend.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.catalina.User;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.configuration.jwtoken.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.AuthenticationException;


@RestController
@RequestMapping ("/auth")
public class AuthController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager; 

    @Autowired
    private UserRepository userrepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest loginRequest) {
        org.springframework.security.core.Authentication authentication;
    
        try {
            // Autenticación del usuario con correo y contraseña
            authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );
        } catch (AuthenticationException exception) {
            // Devolver error si las credenciales son incorrectas
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Bad credentials");
            errorResponse.put("status", false);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    
        // Configurar el contexto de seguridad para el usuario autenticado
        SecurityContextHolder.getContext().setAuthentication(authentication);
    
        // Obtener los detalles del usuario
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    
        // Generar el token JWT
        String jwtToken = jwtUtils.generateTokenFromEmail(userDetails);
    
        // Respuesta exitosa con el token
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("username", userDetails.getUsername());
        successResponse.put("token", jwtToken);
    
        return ResponseEntity.ok(successResponse);
    }    
    

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AuthRequest registerRequest) {
        if(repository.findByEmail(registerRequest.getEmail())){
            return ResponseEntity.badRequest().body("el email ya esta en uso");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER);

        repository.save(user);
        return ResponseEntity.ok("te has registrado correctamente");
    }
    
    
    
    
}
