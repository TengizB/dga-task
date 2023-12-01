package com.tbregvadze.app.controlers;


import com.tbregvadze.app.entities.User;
import com.tbregvadze.app.entities.UserDTO;
import com.tbregvadze.app.models.Response;
import com.tbregvadze.app.models.user.UserListResponse;
import com.tbregvadze.app.models.user.UserResponse;
import com.tbregvadze.app.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "UserController", description = "CRUD operations on users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/secure")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> getSecureResponse() {
        return ResponseEntity.ok(new Response(true,"Hello, Admin !!!"));
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UserListResponse> getAllUsers() {
        try {
            return ResponseEntity.ok(new UserListResponse(true, "Success", userService.getAllUsers()));
        } catch (Exception e) {
            return ResponseEntity.ok(new UserListResponse(false, e.getMessage()));
        }
    }

    @Operation(
            summary = "Get User by ID",
            description = "Retrieve a user by their ID from the system."
    )
    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UserResponse> getUserById(@PathVariable @RequestParam(name = "id", required = true) UUID id) {
        try {
            User user = userService.getUserById(id).orElse(null);
            if(user != null) {
                return ResponseEntity.ok(new UserResponse(true, "Success", user));
            } else {
                return ResponseEntity.ok(new UserResponse(false, "User not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new UserResponse(false, e.getMessage()));
        }
    }

    @PutMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> updateUser(@RequestBody UserDTO user) {
        try {

            userService.updateUser(User.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .build());
            return ResponseEntity.ok(new Response(true,"Success"));
        } catch (Exception e) {
            return ResponseEntity.ok(new UserResponse(false, e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> deleteUser(@PathVariable @RequestParam(name = "id", required = true) UUID id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new Response(true,"Success"));
        } catch (Exception e) {
            return ResponseEntity.ok(new UserResponse(false, e.getMessage()));
        }
    }
}
