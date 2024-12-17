package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    @GetMapping(path = "/admin/get-users")
    public ResponseEntity<List<User>> getUsers(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            List<User> userList = userService.getUsers(authorizationHeader);
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (SecurityException error) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        } catch (AccessDeniedException error) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // Obtener usuario por ID
    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<?> getUserById(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long userId) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            User user = userService.getUserById(authorizationHeader, userId);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getMessage());
        } catch (SecurityException error) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error.getMessage());
        } catch (RuntimeException error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getMessage());
        }
    }

    // Cambiar contraseña
    @PatchMapping("/user/change-password/{userId}")
    public ResponseEntity<User> changePassword(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long userId, @RequestBody User userDetails) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String newPassword = userDetails.getPassword(); // Extrae la nueva contraseña
            User updatedUser = userService.changePassword(authorizationHeader, userId, newPassword);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (SecurityException error) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }
    }

    // Eliminar usuario
    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long userId) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            userService.deleteUser(authorizationHeader, userId);
            return ResponseEntity.status(HttpStatus.OK).body("User succesfully deleted!");

        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getMessage());
        } catch (SecurityException error) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error.getMessage());
        } catch (AccessDeniedException error) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error.getMessage());
        }
    }

    @PatchMapping("/user/add-first-address/{userId}")
    public ResponseEntity<User> addFirstAddress(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long userId, @RequestBody User user) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String newFirstAddress = user.getFirstAddress();
            User updatedUser = userService.addFirstAddress(authorizationHeader, userId, newFirstAddress);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (SecurityException error) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }
    }

    @PatchMapping("/user/add-second-address/{userId}")
    public ResponseEntity<User> addSecondAddress(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long userId, @RequestBody User user) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String newSecondAddress = user.getSecondAddress();
            User updatedUser = userService.addSecondAddress(authorizationHeader, userId, newSecondAddress);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (SecurityException error) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }
    }

    

    // @DeleteMapping("/user/remove-first-address/{userId}")
    // public String deleteFirstAddress(@RequestBody String entity) {
    //     //TODO: process POST request
        
    //     return entity;
    // }

    // @DeleteMapping("/user/remove-second-address/{userId}")
    // public String deleteSecondAddress(@RequestBody String entity) {
    //     //TODO: process POST request
        
    //     return entity;
    // }

    
    
}
