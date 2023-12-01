package com.tbregvadze.app.models.user;

import com.tbregvadze.app.entities.User;
import com.tbregvadze.app.entities.UserDTO;
import com.tbregvadze.app.models.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserResponse extends Response {
    private UserDTO user;

    public UserResponse(Boolean success, String message, User user) {
        super(success, message);
        this.user = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .userRole(user.getRole())
                .build();
    }

    public UserResponse(Boolean success, String message) {
        super(success, message);
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
