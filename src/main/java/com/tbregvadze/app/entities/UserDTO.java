package com.tbregvadze.app.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private UUID id;
    private String email;
    private String firstname;
    private String lastname;
    private UserRole userRole;

    public UserDTO(User user)
    {
        this.id = user.getId();
        this.email = user.getEmail();
        this.userRole = user.getRole();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
    }
}
