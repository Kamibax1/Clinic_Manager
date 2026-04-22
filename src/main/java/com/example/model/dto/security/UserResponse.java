package com.example.model.dto.security;

import com.example.model.entity.UserEntity;
import com.example.model.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

public class UserResponse {
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private boolean enabled;

    @Getter @Setter
    private RoleEnum role;

    public UserResponse() {
    }

    public UserResponse(Long id, String username, String email, boolean enabled, RoleEnum role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.role = role;
    }

    public UserResponse(Long id, String username, String email, RoleEnum role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public static UserResponse fromEntity(UserEntity entity) {
        UserResponse response = new UserResponse();
        response.setId(entity.getId());
        response.setUsername(entity.getUsername());
        response.setEmail(entity.getEmail());
        response.setEnabled(entity.isEnabled());
        response.setRole(entity.getRole().getName());
        return response;
    }
}

