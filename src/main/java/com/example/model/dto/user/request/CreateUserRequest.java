package com.example.model.dto.user.request;

import com.example.model.entity.RoleEntity;
import com.example.model.entity.UserEntity;
import com.example.model.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class CreateUserRequest {
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

    @Getter @Setter
    private boolean enabled;

    @Getter @Setter
    @Size(min = 1, max = 3, message = "Id Role must be between 1 and 3")
    private RoleEnum role;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String username, String password, String email, boolean enabled, RoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.role = role;
    }

    public static UserEntity toEntity(CreateUserRequest request, RoleEntity roleEntity) {
        UserEntity entity = new UserEntity();
        entity.setUsername(request.getUsername());
        entity.setPassword(request.getPassword());
        entity.setEmail(request.getEmail());
        entity.setEnabled(request.isEnabled());
        entity.setRole(roleEntity);
        return entity;
    }
}
