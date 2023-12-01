package com.tbregvadze.app.controlers;

import com.tbregvadze.app.models.Response;
import com.tbregvadze.app.models.auth.AuthenticationRequest;
import com.tbregvadze.app.models.auth.AuthenticationResponse;
import com.tbregvadze.app.models.auth.RegisterRequest;
import com.tbregvadze.app.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> register(@RequestBody RegisterRequest request) {
        Response register = authenticationService.register(request);
        if(register.getSuccess()) {
            return ResponseEntity.ok(register);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(register);
        }
    }

    @PostMapping(value = "/login",  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticate = authenticationService.authenticate(request);
        if(authenticate.getSuccess()) {
            return ResponseEntity.ok(authenticate);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authenticate);
        }
    }
}
