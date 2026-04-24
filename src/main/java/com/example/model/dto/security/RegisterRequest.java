package com.example.model.dto.security;

import com.example.model.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class RegisterRequest {
    @Getter @Setter
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Getter @Setter
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Getter @Setter
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    public static UserEntity toEntity(RegisterRequest request) {
        UserEntity entity = new UserEntity();
        entity.setUsername(request.getUsername());
        entity.setPassword(request.getPassword());
        entity.setEmail(request.getEmail());
        return entity;
    }
}
