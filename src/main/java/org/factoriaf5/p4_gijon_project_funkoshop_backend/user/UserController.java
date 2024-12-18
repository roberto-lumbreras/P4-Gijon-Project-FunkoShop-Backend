package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            userService.addUser(user);

            return ResponseEntity.ok("Te has registrado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

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

    @PatchMapping("/admin/active/{userId}")
    public ResponseEntity<Map<String, String>> activeUser(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long userId) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            boolean isActive = userService.activeUserById(authorizationHeader, userId);

            if (isActive) {
                Map<String, String> response = Map.of("Usuario activo: ", "true");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Map<String, String> response = Map.of("Usuario activo: ", "false");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        } catch (SecurityException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

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
    public ResponseEntity<User> addFirstAddress(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long userId, @RequestBody User user) {
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
    public ResponseEntity<User> addSecondAddress(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long userId, @RequestBody User user) {
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

    @DeleteMapping("/user/remove-second-address/{userId}")
    public ResponseEntity<?> deleteSecondAddress(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long userId) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            userService.removeSecondAddress(authorizationHeader, userId);

            return ResponseEntity.ok("Segunda dirección eliminada con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());

        }
    }

    @PatchMapping("/user/change-shipping-address/{userId}")
    public ResponseEntity<User> changeShippingAddress(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long userId, @RequestBody User user) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            Boolean newShippingAddress = user.getShippingAddress();
            User updatedUser = userService.changeShippingAddress(authorizationHeader, userId, newShippingAddress);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (SecurityException error) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }
    }
}