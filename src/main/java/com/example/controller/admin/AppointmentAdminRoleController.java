package com.example.controller.admin;

import com.example.exception.ResourceNotFoundException;
import com.example.model.dto.appointment.response.AppointmentFullInformationResponse;
import com.example.model.enums.StatusEnum;
import com.example.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/appointments")
@Tag(name = "Записи")
public class AppointmentAdminRoleController {
    private final AppointmentService appointmentService;
    public AppointmentAdminRoleController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Operation(summary = "Удалить запись по ID")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if(appointmentService.existsById(id)){
            appointmentService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Обновить статус у любой записи")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/information/full/status/{id}")
    public ResponseEntity<AppointmentFullInformationResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody StatusEnum status
    ) {
        try {
            return ResponseEntity.ok(appointmentService.updateStatus(id, status));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Обновить симптомы у любой записи")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/information/full/symptoms/{id}")
    public ResponseEntity<AppointmentFullInformationResponse> updateSymptoms(
            @PathVariable Long id,
            @RequestBody String symptoms
    ) {
        try {
            return ResponseEntity.ok(appointmentService.updateSymptoms(id, symptoms));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
