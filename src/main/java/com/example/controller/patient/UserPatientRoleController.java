package com.example.controller.patient;

import com.example.exception.ResourceNotFoundException;
import com.example.model.dto.patient.response.PatientShortInfoResponse;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/patient/users")
@Tag(name = "Пользователи")
public class UserPatientRoleController {
    private final UserService userService;
    public UserPatientRoleController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Получить свои данные пациента")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/me/{username}")
    public ResponseEntity<PatientShortInfoResponse> getPatientByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(userService.findPatientByUsername(username));
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
