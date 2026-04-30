package com.example.controller.patient;

import com.example.exception.ResourceNotFoundException;
import com.example.model.dto.patient.request.UpdatePatientFullInformationRequest;
import com.example.model.dto.patient.response.UpdatePatientFullInformationResponse;
import com.example.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient/patients")
@Tag(name = "Пациенты")
public class PatientPatientRoleController {
    private final PatientService patientService;
    public PatientPatientRoleController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Operation(summary = "Обновить полную информацию о пациенте")
    @SecurityRequirement(name = "bearer-jwt")
    @PutMapping("/information/full/{id}")
    public ResponseEntity<UpdatePatientFullInformationResponse> updateFullInfo(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePatientFullInformationRequest request
    ){
        try {
            return ResponseEntity.ok(patientService.updateFullInfo(id, request));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
