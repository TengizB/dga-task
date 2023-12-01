package com.tbregvadze.app.controlers;

import com.tbregvadze.app.models.Response;
import com.tbregvadze.app.models.auth.AuthenticationRequest;
import com.tbregvadze.app.models.auth.AuthenticationResponse;
import com.tbregvadze.app.models.auth.RegisterRequest;
import com.tbregvadze.app.reposiories.UserRepository;
import com.tbregvadze.app.services.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.config.name=application-test")
public class AuthenticationControllerTest {

    @Autowired
    private AuthenticationController authController;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }
    
    @Test
    public void testRegisterUser() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password");
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");

        ResponseEntity<Response> response = authController.register(registerRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
        assertEquals("Success", response.getBody().getMessage());
    }

    @Test
    public void testUserLogin() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password");
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");

        ResponseEntity<Response> response = authController.register(registerRequest);
        assertTrue(response.getBody().getSuccess());


        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("test@example.com");
        authenticationRequest.setPassword("password");

        ResponseEntity<AuthenticationResponse> loginResponse = authController.login(authenticationRequest);
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertNotNull(loginResponse.getBody());
        assertTrue(loginResponse.getBody().getSuccess());
        assertEquals("Success", loginResponse.getBody().getMessage());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(registerRequest.getEmail());

        //Check if token is valid
        assertTrue(jwtService.isTokenValid(loginResponse.getBody().getToken(), userDetails));

    }

    @Test
    public void testRegistrationFailedDueToExistingEmail() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("existing@example.com");
        registerRequest.setPassword("password");
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");

        ResponseEntity<Response> response1 = authController.register(registerRequest);
        assertTrue(response1.getBody().getSuccess());

        ResponseEntity<Response> response2 = authController.register(registerRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
        assertNotNull(response2.getBody());
        assertFalse(response2.getBody().getSuccess());
        assertEquals("Email is already in use", response2.getBody().getMessage());
    }

    @Test
    public void testLoginFailedDueToWrongPassword() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password");
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");

        ResponseEntity<Response> response1 = authController.register(registerRequest);
        assertTrue(response1.getBody().getSuccess());

        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("test@example.com");
        authenticationRequest.setPassword("wrongpassword");

        ResponseEntity<AuthenticationResponse> loginResponse = authController.login(authenticationRequest);
        assertEquals(HttpStatus.UNAUTHORIZED, loginResponse.getStatusCode());
        assertNotNull(loginResponse.getBody());
        assertFalse(loginResponse.getBody().getSuccess());
        assertEquals("Bad credentials", loginResponse.getBody().getMessage());
    }

}