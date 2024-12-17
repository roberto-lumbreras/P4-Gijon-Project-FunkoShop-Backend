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

    // Simulamos un encabezado JWT para las pruebas
    private String generateAuthorizationHeader() {
        return "Bearer fake-jwt-token";
    }

    @Test
    public void testSignup_Success() throws Exception {
        User user = User.builder()
                .id(1L)
                .username("testuser")
                .email("testemail@example.com")
                .password("password123")
                .enabled(true)
                .role(Role.ROLE_USER)  // Asignar un rol para que no sea null
                .build();

        doNothing().when(userService).addUser(user);

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"username\":\"testuser\",\"email\":\"testemail@example.com\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Te has registrado correctamente"));
    }

    @Test
    public void testGetUsers() throws Exception {
        List<User> users = Arrays.asList(
                User.builder().id(1L).username("user1").email("email1@example.com").password("password1").build(),
                User.builder().id(2L).username("user2").email("email2@example.com").password("password2").build()
        );

        // Cambié la llamada de getUsers a un método que pasa el encabezado de autorización
        when(userService.getUsers(generateAuthorizationHeader())).thenReturn(users);

        mockMvc.perform(get("/api/admin/")
                .header("Authorization", generateAuthorizationHeader()))  // Aquí pasamos el encabezado JWT
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[1].username").value("user2"));
    }

    @Test
    public void testGetUserById_Success() throws Exception {
        User user = User.builder().id(1L).username("user1").email("email1@example.com").password("password1").build();

        // Aquí cambiamos la llamada a getUserById pasando el encabezado de autorización
        when(userService.getUserById(generateAuthorizationHeader(), 1L)).thenReturn(user);

        mockMvc.perform(get("/api/user/1")
                .header("Authorization", generateAuthorizationHeader()))  // Pasar el encabezado JWT
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    public void testChangePassword_Success() throws Exception {
        User updatedUser = User.builder().id(1L).username("user1").email("email1@example.com").password("newpassword").build();

        // Aquí cambiamos la llamada a changePassword pasando el encabezado y el nuevo password
        when(userService.changePassword(generateAuthorizationHeader(), 1L, "newpassword")).thenReturn(updatedUser);

        mockMvc.perform(patch("/api/user/1")
                .header("Authorization", generateAuthorizationHeader())  // Pasar el encabezado JWT
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\":\"newpassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value("newpassword"));
    }

    @Test
    public void testDeleteUser_Success() throws Exception {
        User user = User.builder().id(1L).username("user1").email("email1@example.com").password("password1").build();

        when(userService.getUserById(generateAuthorizationHeader(), 1L)).thenReturn(user);
        doNothing().when(userService).deleteUser(generateAuthorizationHeader(), 1L);

        mockMvc.perform(delete("/api/user/1")
                .header("Authorization", generateAuthorizationHeader()))  // Pasar el encabezado JWT
                .andExpect(status().isNoContent());
    }
}
