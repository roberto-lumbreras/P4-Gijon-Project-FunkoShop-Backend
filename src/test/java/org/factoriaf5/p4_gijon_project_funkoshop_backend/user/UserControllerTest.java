package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignupSuccess() {
        User user = new User();
        doNothing().when(userService).addUser(user);
        ResponseEntity<?> response = userController.signup(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Te has registrado correctamente", response.getBody());
    }

    @Test
    void testSignupFailure() {
        User user = new User();
        doThrow(new RuntimeException("Error al registrar usuario")).when(userService).addUser(user);
        ResponseEntity<?> response = userController.signup(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error al registrar usuario", response.getBody());
    }

    @Test
    void testGetUsersUnauthorized() {
        ResponseEntity<List<User>> response = userController.getUsers(null);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testGetUsersSuccess() throws Exception {
        String token = "Bearer valid_token";
        List<User> users = new ArrayList<>();
        when(userService.getUsers(token)).thenReturn(users);
        ResponseEntity<List<User>> response = userController.getUsers(token);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    void testGetUsersForbidden() throws Exception {
        String token = "Bearer invalid_token";
        when(userService.getUsers(token)).thenThrow(new AccessDeniedException("Access Denied"));
        ResponseEntity<List<User>> response = userController.getUsers(token);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testActiveUserSuccess() throws Exception {
        String token = "Bearer valid_token";
        Long userId = 1L;
        when(userService.activeUserById(token, userId)).thenReturn(true);
        ResponseEntity<Map<String, String>> response = userController.activeUser(token, userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("true", response.getBody().get("Usuario activo: "));
    }

    @Test
    void testActiveUserNotFound() throws Exception {
        String token = "Bearer valid_token";
        Long userId = 1L;
        when(userService.activeUserById(token, userId)).thenThrow(new IllegalArgumentException("User not found"));
        ResponseEntity<Map<String, String>> response = userController.activeUser(token, userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody().get("message"));
    }

    @Test
    void testDeleteUserSuccess() throws Exception {
        String token = "Bearer valid_token";
        Long userId = 1L;
        doNothing().when(userService).deleteUser(token, userId);
        ResponseEntity<String> response = userController.deleteUser(token, userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User succesfully deleted!", response.getBody());
    }

    @Test
    void testDeleteUserForbidden() throws Exception {
        String token = "Bearer invalid_token";
        Long userId = 1L;
        doThrow(new AccessDeniedException("Access Denied")).when(userService).deleteUser(token, userId);
        ResponseEntity<String> response = userController.deleteUser(token, userId);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Access Denied", response.getBody());
    }
}