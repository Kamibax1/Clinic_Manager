package com.example.model.dto.security;

import lombok.Getter;
import lombok.Setter;

public class AuthResponse {
    @Getter @Setter
    private String token;

    @Getter @Setter
    private String type = "Bearer";

    @Getter @Setter
    private UserResponse user;

    public AuthResponse() {
    }

    public AuthResponse(String token, String type, UserResponse user) {
        this.token = token;
        this.type = type;
        this.user = user;
    }

    public AuthResponse(String token, UserResponse user) {
        this.token = token;
        this.user = user;
    }
}
