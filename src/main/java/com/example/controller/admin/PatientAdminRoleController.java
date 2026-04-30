package com.example.controller.admin;

import com.example.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/patients")
@Tag(name = "Пациенты")
public class PatientAdminRoleController {
    private final PatientService patientService;
    public PatientAdminRoleController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Operation(summary = "Удалить user по ID пациента")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if(patientService.existsById(id)){
            patientService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
