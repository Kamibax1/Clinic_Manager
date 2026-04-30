package com.example.controller.admin;

import com.example.exception.ResourceNotFoundException;
import com.example.model.dto.doctor.response.DoctorFullInformationResponse;
import com.example.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/doctors")
@Tag(name = "Врачи")
public class DoctorAdminRoleController {
    private final DoctorService doctorService;
    public DoctorAdminRoleController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Operation(summary = "Удалить user по ID врача")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if(doctorService.existsById(id)){
            doctorService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
