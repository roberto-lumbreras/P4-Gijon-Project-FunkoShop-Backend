package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;

import java.nio.file.AccessDeniedException;
import java.util.List;
/* import java.util.Optional; */

import org.factoriaf5.p4_gijon_project_funkoshop_backend.configuration.jwtoken.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final JdbcTemplate jdbcTemplate;

    // Constructor con inyección de dependencias
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils,
            JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Obtener usuario por ID
    public User getUserById(String authorizationHeader, Long userId) {
        String token = authorizationHeader.substring(7);
        String emailFromToken = jwtUtils.getEmailFromJwtToken(token);

        User userRequest = userRepository.findByEmail(emailFromToken)
                .orElseThrow(() -> new IllegalArgumentException("User request not found"));

        if (!userRequest.getJwToken().equals(token)) {
            throw new SecurityException("User request token don't match with user's BDD token");
        }

        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    // Obtener todos los usuarios
    public List<User> getUsers(String authorizationHeader) throws AccessDeniedException {
        String token = authorizationHeader.substring(7);
        String emailFromToken = jwtUtils.getEmailFromJwtToken(token);

        User userRequest = userRepository.findByEmail(emailFromToken)
                .orElseThrow(() -> new IllegalArgumentException("User request not found"));

        // System.out.println(userRequest);
        if (!userRequest.getJwToken().equals(token)) {
            throw new SecurityException("User request token don't match with user's BDD token");
        }

        if (!"ROLE_ADMIN".equals(userRequest.getRole())) {
            throw new AccessDeniedException("Access DENIED. User not authorizated, only ADMIN");
        }

        List<User> list = userRepository.findAll();

        return userRepository.findAll();
    }

    // registrar usuario
    public User addUser(User user) {
        // Verificar si el email ya está registrado
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use: " + user.getEmail());
        }

        // Asignar automáticamente el email como username
        user.setUsername(user.getEmail());
        user.setEmail(user.getEmail());
        user.setEnabled(true);

        // Codificar la contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Asignar un rol predeterminado si no se pasa uno en el DTO
        if (user.getRole() == null) {
            user.setRole(Role.ROLE_USER); // Rol predeterminado
        }

        // Guardar el usuario en la base de datos
        User savedUser = userRepository.save(user);

        String authorityQuery = "INSERT INTO authorities (username, authority) VALUES ('" + user.getEmail() + "', '"
                + user.getRole() + "')";
        jdbcTemplate.update(authorityQuery);

        return savedUser;
    }

    // Eliminar usuario por ID
    public void deleteUser(String authorizationHeader, Long userId) throws AccessDeniedException {
        String token = authorizationHeader.substring(7);
        String emailFromToken = jwtUtils.getEmailFromJwtToken(token);

        User userRequest = userRepository.findByEmail(emailFromToken)
                .orElseThrow(() -> new IllegalArgumentException("User request not found"));

        if (!userRequest.getJwToken().equals(token)) {
            throw new SecurityException("User request token don't match with user's BDD token");
        }

        if (!"ROLE_ADMIN".equals(userRequest.getRole())) {
            throw new AccessDeniedException("Access DENIED. User not authorizated, only ADMIN");
        }

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User to delete no found with ID: " + userId));

        userRepository.deleteById(userId);
    }

    // Cambiar contraseña
    public User changePassword(String authorizationHeader, Long userId, String newPassword) {
        String token = authorizationHeader.substring(7);
        String emailFromToken = jwtUtils.getEmailFromJwtToken(token);

        User userRequest = userRepository.findByEmail(emailFromToken)
                .orElseThrow(() -> new IllegalArgumentException("User request not found"));

        if (!userRequest.getJwToken().equals(token)) {
            throw new SecurityException("User request token don't match with user's BDD token");
        }

        User userToFind = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User to find on BD not found"));

        userToFind.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(userToFind);
    }

    public User addFirstAddress(String authorizationHeader, Long userId, String firstAddress) {
        String token = authorizationHeader.substring(7);
        String emailFromToken = jwtUtils.getEmailFromJwtToken(token);

        User userRequest = userRepository.findByEmail(emailFromToken)
                .orElseThrow(() -> new IllegalArgumentException("User request not found"));

        if (!userRequest.getJwToken().equals(token)) {
            throw new SecurityException("User request token don't match with user's BDD token");
        }

        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User to find on BD not found"));

        userToUpdate.setFirstAddress(firstAddress);
        return userRepository.save(userToUpdate);
    }

    public User addSecondAddress(String authorizationHeader, Long userId, String secondAddress) {
        String token = authorizationHeader.substring(7);
        String emailFromToken = jwtUtils.getEmailFromJwtToken(token);

        User userRequest = userRepository.findByEmail(emailFromToken)
                .orElseThrow(() -> new IllegalArgumentException("User request not found"));

        if (!userRequest.getJwToken().equals(token)) {
            throw new SecurityException("User request token don't match with user's BDD token");
        }

        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User to find on BD not found"));
        
        
        if(userToUpdate.getFirstAddress() == null){
            throw new IllegalArgumentException("The user needs first address");
        }

        userToUpdate.setSecondAddress(secondAddress);
        return userRepository.save(userToUpdate);
    }
}
