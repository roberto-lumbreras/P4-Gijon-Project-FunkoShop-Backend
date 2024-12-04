package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
/* import java.util.Optional; */

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor con inyección de dependencias
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Obtener usuario por ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    // Obtener todos los usuarios
    public List<User> getUsers() {
        return userRepository.findAll();
    }

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
            user.setRole(Role.USER); // Rol predeterminado
        }
    
        // Guardar el usuario en la base de datos
        return userRepository.save(user);
    }
    // Eliminar usuario por ID
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    // Cambiar contraseña
    public User changePassword(Long id, String newPassword) {
        User user = getUserById(id);
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }
}
