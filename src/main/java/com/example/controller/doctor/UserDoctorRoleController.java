package com.example.controller.doctor;

import com.example.exception.ResourceNotFoundException;
import com.example.model.dto.doctor.response.DoctorShortInfoResponse;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/doctor/users")
@Tag(name = "Пользователи")
public class UserDoctorRoleController {
    private final UserService userService;
    public UserDoctorRoleController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Получить свои данные врача")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/me/{username}")
    public ResponseEntity<DoctorShortInfoResponse> getDoctorByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(userService.findDoctorByUsername(username));
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
