package com.example.controller;

import com.example.exception.ResourceNotFoundException;
import com.example.model.dto.security.*;
import com.example.model.entity.UserEntity;
import com.example.repository.UserRepository;
import com.example.service.JwtService;
import com.example.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.example.exception.ValidationException;

@Slf4j
@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login attempt for username: {}", request.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserEntity userEntity = userRepository.findByUsername(request.getUsername());
        if (userEntity == null) {
            log.warn("Login failed: user '{}' not found", request.getUsername());
            throw new ResourceNotFoundException("User", "username", request.getUsername());
        }

        String token = jwtService.generateToken(userEntity);
        UserResponse userResponse = UserResponse.fromEntity(userEntity);
        AuthResponse response = new AuthResponse(token, userResponse);

        log.info("User '{}' logged in successfully", request.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) throws ValidationException {
        log.info("Registration attempt for username: {}", request.getUsername());
        UserResponse userResponse = userService.register(request);

        UserEntity userEntity = userRepository.findByUsername(request.getUsername());
        if (userEntity == null) {
            throw new ResourceNotFoundException("User", "username", request.getUsername());
        }

        String token = jwtService.generateToken(userEntity);

        AuthResponse response = new AuthResponse(token, userResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        UserEntity userEntity = userRepository.findByUsername(authentication.getName());
        if (userEntity == null) {
            throw new ResourceNotFoundException("User", "username", authentication.getName());
        }

        return ResponseEntity.ok(UserResponse.fromEntity(userEntity));
    }
}
