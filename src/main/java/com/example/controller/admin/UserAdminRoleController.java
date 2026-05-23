package com.example.controller.admin;

import com.example.exception.ResourceNotFoundException;
import com.example.model.dto.user.request.CreateUserRequest;
import com.example.model.dto.security.UserResponse;
import com.example.model.dto.user.UserFullInfoResponse;
import com.example.model.dto.user.request.UpdateUserFullInfoRequest;
import com.example.model.enums.RoleEnum;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin/users")
@Tag(name = "Пользователи")
public class UserAdminRoleController {
    private final UserService userService;
    public UserAdminRoleController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Создать нового пользователя")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponse> save(@Valid @RequestBody CreateUserRequest request) {
        log.info("Attempting to save for username: {}", request.getUsername());
        try {
            UserResponse userResponse = userService.save(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        } catch (ResourceNotFoundException e) {
            log.info("Username '{}' not found", request.getUsername());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Получить всех пользователей")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserResponse> users = userService.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Получить всех пользователей по их активности (активных или заблокированных)")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/enabled/{enabled}")
    public ResponseEntity<List<UserResponse>> findAllEnabled(@PathVariable("enabled") Boolean enabled) {
        List<UserResponse> users = userService.findAllByEnabled(enabled);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Получить всех пользователей по их роли")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<UserResponse>> findAllByRoleName(@PathVariable("roleName") RoleEnum roleName) {
        List<UserResponse> users = userService.findAllByRoleName(roleName);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Получить всех пользователей, отсортированных по имени пользователя")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/order/username")
    public ResponseEntity<List<UserResponse>> findAllByOrderByUsername() {
        List<UserResponse> users = userService.findAllByOrderByUsername();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Получить всех пользователей, отсортированных по почте пользователя")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/order/email")
    public ResponseEntity<List<UserResponse>> findAllByOrderByEmail() {
        List<UserResponse> users = userService.findAllByOrderByEmail();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Получить всех пользователей по части их имени")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/containing/username/{username}")
    public ResponseEntity<List<UserResponse>> findAllByUsernameContaining(@PathVariable("username") String username) {
        List<UserResponse> users = userService.findAllByUsernameContaining(username);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Получить полную информацию о пользователе")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/information/full/{id}")
    public ResponseEntity<UserFullInfoResponse> findFullInfoByUserId(@PathVariable("id") long id) {
        try {
            return ResponseEntity.ok(userService.findFullInfoByUserId(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Изменить статус активности у пользователя")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/enabled/{id}")
    public ResponseEntity<UserResponse> updateEnabled(@PathVariable long id) {
        try {
            return ResponseEntity.ok(userService.updateEnabled(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Изменить роль у пользователя")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/role/{id}")
    public ResponseEntity<UserResponse> updateRole(
            @PathVariable long id,
            @RequestBody RoleEnum role
    ) {
        try {
            return ResponseEntity.ok(userService.updateRole(id, role));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Изменение всех данных пользователя")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/information/full/{id}")
    public ResponseEntity<UserFullInfoResponse> updateFullInfo(
            @PathVariable long id,
            @RequestBody UpdateUserFullInfoRequest request
    ) {
        try {
            return ResponseEntity.ok(userService.updateUserFullInfo(id, request));
        }  catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
