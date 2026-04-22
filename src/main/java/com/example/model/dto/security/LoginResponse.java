package com.example.model.dto.security;

import lombok.Getter;
import lombok.Setter;

public class LoginResponse {
    @Getter @Setter
    private String message;

    @Getter @Setter
    private UserResponse user;

    public LoginResponse(String message, UserResponse user) {
        this.message = message;
        this.user = user;
    }
}
