package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Crear un nuevo usuario
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            // Llamar al servicio para crear el usuario
            userService.addUser(user);

            // Respuesta exitosa
            return ResponseEntity.ok("Te has registrado correctamente");
        } catch (RuntimeException e) {
            // Si el email ya está en uso, el servicio lanzará una excepción
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Obtener todos los usuarios
    @GetMapping(path = "/admin/")
    public ResponseEntity<List<User>> getUsers() {
        List<User> user = userService.getUsers();
        return ResponseEntity.ok(user);
    }

    // Obtener usuario por ID
    @GetMapping(path = "/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    // Cambiar contraseña
    @PatchMapping("/user/{id}")
    public ResponseEntity<User> changePassword(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            String newPassword = userDetails.getPassword(); // Extrae la nueva contraseña
            User updatedUser = userService.changePassword(id, newPassword);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Eliminar usuario
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.getUserById(id) != null) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
