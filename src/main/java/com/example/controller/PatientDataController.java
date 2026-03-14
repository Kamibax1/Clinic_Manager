package com.example.controller;

import com.example.model.dto.profile.PatientDataDTO;
import com.example.service.PatientDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data/patients")
public class PatientDataController {

    private PatientDataService patientDataService;

    public PatientDataController(PatientDataService patientDataService) {
        this.patientDataService = patientDataService;
    }

    @GetMapping
    public ResponseEntity<List<PatientDataDTO>> findAllPatients(){
        List<PatientDataDTO> patients = patientDataService.findAll();
        if(patients.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDataDTO> updatePatient(
            @PathVariable Long id,
            @RequestBody PatientDataDTO newPatientDataDTO) {
        return patientDataService.update(id, newPatientDataDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
