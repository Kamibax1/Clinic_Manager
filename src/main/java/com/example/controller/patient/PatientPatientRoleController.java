package com.example.controller.patient;

import com.example.model.dto.patient.request.UpdatePatientFullInformationRequest;
import com.example.model.dto.patient.response.UpdatePatientFullInformationResponse;
import com.example.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient/patients")
public class PatientPatientRoleController {
    private final PatientService patientService;
    public PatientPatientRoleController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PutMapping("/information/full/{id}")
    public ResponseEntity<UpdatePatientFullInformationResponse> updateFullInfo(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePatientFullInformationRequest request
    ){
        return patientService.updateFullInfo(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
