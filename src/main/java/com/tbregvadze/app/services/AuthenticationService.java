package com.tbregvadze.app.services;

import com.tbregvadze.app.entities.User;
import com.tbregvadze.app.entities.UserRole;
import com.tbregvadze.app.models.Response;
import com.tbregvadze.app.models.auth.AuthenticationRequest;
import com.tbregvadze.app.models.auth.AuthenticationResponse;
import com.tbregvadze.app.models.auth.RegisterRequest;
import com.tbregvadze.app.reposiories.UserRepository;
import com.tbregvadze.app.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    public Response register(RegisterRequest request) {
        try {

            if(userRepository.findByEmail(request.getEmail()).isPresent())
                return new Response(false, "Email is already in use");
            User user = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .role(UserRole.USER)
                    .build();

            userRepository.save(user);
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
        return new Response(true, "Success");
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            Object[] token = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token((String) token[0])
                    .expirationDate((Date) token[1])
                    .success(true)
                    .message("Success")
                    .build();
        } catch (BadCredentialsException e) {
            return new AuthenticationResponse(false, "Bad credentials");
        } catch (Exception e) {
            return new AuthenticationResponse(false, "Fail");
        }
    }
}
