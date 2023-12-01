package com.tbregvadze.app.services;

import com.tbregvadze.app.entities.User;
import com.tbregvadze.app.reposiories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public static final String USERS = "users";

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Cacheable(value = USERS, key = "#id")
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @CachePut(value = USERS, key = "#result.id")
    public User createUser(User user) {
        if (isEmailExists(user.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }
        return userRepository.save(user);
    }

    @CachePut(value = USERS, key = "#user.id")
    public User updateUser(User user) {
        if(userRepository.existsByEmailAndNotUserId(user.getEmail(), user.getId())) {
            throw new RuntimeException("Email is already in use");
        }

        Optional<User> optional = userRepository.findById(user.getId());
        if (optional.isPresent()) {
            User userToUpdate = optional.get();
            if(user.getFirstname() != null) userToUpdate.setFirstname(user.getFirstname());
            if(user.getLastname() != null) userToUpdate.setLastname(user.getLastname());
            if(user.getEmail() != null) userToUpdate.setEmail(user.getEmail());
            return userRepository.save(userToUpdate);
        }
        throw new RuntimeException("User not found");
    }

    @CacheEvict(value = USERS, key = "#id")
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    private boolean isEmailExists(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        return existingUser.isPresent();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();
            return userRepository.findByEmail(username)
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        }

        throw new RuntimeException("Unable to retrieve authenticated user");
    }
}
