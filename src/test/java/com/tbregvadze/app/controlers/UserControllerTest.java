package com.tbregvadze.app.controlers;

import com.tbregvadze.app.entities.User;
import com.tbregvadze.app.entities.UserDTO;
import com.tbregvadze.app.entities.UserRole;
import com.tbregvadze.app.models.Response;
import com.tbregvadze.app.models.user.UserListResponse;
import com.tbregvadze.app.models.user.UserResponse;
import com.tbregvadze.app.reposiories.UserRepository;
import com.tbregvadze.app.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.config.name=application-test")
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userRepository.deleteAll();
    }

    @Test
    public void testCreateUsers() {
        User user1 = new User(UUID.randomUUID(), "user1@example.com", "12345", "One", "One", UserRole.USER);

        User user1Saved = userService.createUser(user1);
        assertNotNull(user1Saved);
        assertEquals(user1, user1Saved);
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User(UUID.randomUUID(), "user1@example.com", "12345", "One", "One", UserRole.USER);
        User user2 = new User(UUID.randomUUID(), "user2@example.com", "12345", "Two", "Two", UserRole.ADMIN);

        User user1Saved = userService.createUser(user1);
        assertEquals(user1, user1Saved);
        User user2Saved = userService.createUser(user2);
        assertEquals(user2, user2Saved);


        ResponseEntity<UserListResponse> response = userController.getAllUsers();

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getUsers());
        assertEquals(2, response.getBody().getUsers().size());
        assertTrue(response.getBody().getSuccess());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetUserById() {
        User user = new User(null, "tbregvadze@proxima.solutions", "12345", "Tengiz", "Bregvadze", UserRole.USER);
        user = userService.createUser(user);

        ResponseEntity<UserResponse> response = userController.getUserById(user.getId());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
        assertNotNull(response.getBody().getUser());
        assertTrue(user.getId().equals(response.getBody().getUser().getId()));
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetUserById_UserNotFound() {
        UUID userId = UUID.randomUUID();

        ResponseEntity<UserResponse> response = userController.getUserById(userId);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getSuccess());
        assertNull(response.getBody().getUser());
        assertEquals("User not found", response.getBody().getMessage());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testUpdateUser() {
        UserDTO user = new UserDTO(null, "tbregvadze@proxima.solutions", "Tengiz", "Bregvadze", UserRole.USER);
        user = new UserDTO(userRepository.save(
                User.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .build()
        ));

        user.setFirstname("Updated Name");
        user.setFirstname("Updated Lastname");
        user.setEmail("new@proxima.solutions");

        ResponseEntity<Response> response = userController.updateUser(user);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
        assertEquals(200, response.getStatusCodeValue());


        UserResponse response2 = userController.getUserById(user.getId()).getBody();
        assertNotNull(response2);
        assertTrue(response2.getSuccess());
        assertEquals(response2.getUser().getFirstname(), user.getFirstname());
        assertEquals(response2.getUser().getLastname(), user.getLastname());
        assertEquals(response2.getUser().getEmail(), user.getEmail());
    }

    @Test
    public void testUpdateUser_EmailInUse() {
        User user1 = new User(null, "tbregvadze@proxima.solutions", "12345", "Tengiz", "Bregvadze", UserRole.USER);
        User user2 = new User(null, "beka@proxima.solutions", "12345", "Updated", "Updated", UserRole.USER);

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);

        user1.setEmail("beka@proxima.solutions");

        ResponseEntity<Response> response = userController.updateUser(new UserDTO(user1));
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getSuccess());
        assertEquals(response.getBody().getMessage(), "Email is already in use");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testDeleteUser() {
        User user = new User(null, "tbregvadze@proxima.solutions", "12345", "Tengiz", "Bregvadze", UserRole.USER);
        user = userService.createUser(user);

        ResponseEntity<UserResponse> userResponse = userController.getUserById(user.getId());
        assertNotNull(userResponse.getBody());
        assertTrue(userResponse.getBody().getSuccess());
        assertNotNull(userResponse.getBody().getUser());
        assertTrue(user.getId().equals(userResponse.getBody().getUser().getId()));
        assertEquals(200, userResponse.getStatusCodeValue());

        ResponseEntity<Response> response = userController.deleteUser(user.getId());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
        assertEquals(200, response.getStatusCodeValue());

        userResponse = userController.getUserById(user.getId());
        assertNotNull(userResponse.getBody());
        assertFalse(userResponse.getBody().getSuccess());
        assertNull(userResponse.getBody().getUser());
        assertEquals(200, userResponse.getStatusCodeValue());
    }
}