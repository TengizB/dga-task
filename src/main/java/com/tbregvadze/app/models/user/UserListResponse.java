package com.tbregvadze.app.models.user;

import com.tbregvadze.app.entities.User;
import com.tbregvadze.app.entities.UserDTO;
import com.tbregvadze.app.models.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class UserListResponse extends Response {
    private List<UserDTO> users = new ArrayList<>();

    public UserListResponse(Boolean success, String message) {
        super(success, message);
    }

    public UserListResponse(Boolean success, String message, @NotNull List<User> users) {
        super(success, message);
        users.forEach(i -> this.users.add(new UserDTO(
                i.getId(),
                i.getEmail(),
                i.getFirstname(),
                i.getLastname(),
                i.getRole()
        )));
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
