package com.tbregvadze.app.models.auth;

import com.tbregvadze.app.models.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse extends Response {
    private String token;
    private Date expirationDate;

    public AuthenticationResponse(Boolean success, String message) {
        super(success, message);
    }
}
