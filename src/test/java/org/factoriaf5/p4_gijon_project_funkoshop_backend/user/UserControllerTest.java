package org.factoriaf5.p4_gijon_project_funkoshop_backend.user;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testSignup_Success() throws Exception {
        User user = new User(1L, "testuser", "testemail@example.com", "password123", null, null, null);

        doNothing().when(userService).addUser(user);

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"username\":\"testuser\",\"email\":\"testemail@example.com\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Te has registrado correctamente"));
    }

    @Test
    public void testSignup_EmailAlreadyInUse() throws Exception {
        User user = new User(1L, "testuser", "testemail@example.com", "password123", null, null, null);

        doThrow(new RuntimeException("El email ya está en uso")).when(userService).addUser(user);

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"username\":\"testuser\",\"email\":\"testemail@example.com\",\"password\":\"password123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El email ya está en uso"));
    }

    @Test
    public void testGetUsers() throws Exception {
        List<User> users = Arrays.asList(
                new User(1L, "user1", "email1@example.com", "password1", null, null, null),
                new User(2L, "user2", "email2@example.com", "password2", null, null, null)
        );

        when(userService.getUsers()).thenReturn(users);

        mockMvc.perform(get("/api/admin/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[1].username").value("user2"));
    }

    @Test
    public void testGetUserById_Success() throws Exception {
        User user = new User(1L, "user1", "email1@example.com", "password1", null, null, null);

        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    public void testGetUserById_NotFound() throws Exception {
        when(userService.getUserById(1L)).thenThrow(new RuntimeException("Usuario no encontrado"));

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testChangePassword_Success() throws Exception {
        User updatedUser = new User(1L, "user1", "email1@example.com", "newpassword", null, null, null);

        when(userService.changePassword(1L, "newpassword")).thenReturn(updatedUser);

        mockMvc.perform(patch("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\":\"newpassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value("newpassword"));
    }

    @Test
    public void testChangePassword_UserNotFound() throws Exception {
        when(userService.changePassword(1L, "newpassword")).thenThrow(new RuntimeException("Usuario no encontrado"));

        mockMvc.perform(patch("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\":\"newpassword\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUser_Success() throws Exception {
        User user = new User(1L, "user1", "email1@example.com", "password1", null, null, null);

        when(userService.getUserById(1L)).thenReturn(user);
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteUser_NotFound() throws Exception {
        when(userService.getUserById(1L)).thenThrow(new RuntimeException("Usuario no encontrado"));

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isNotFound());
    }
}
