package com.example.controller;

import com.example.model.dto.PatientDTO;
import com.example.service.PatientDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data/patients")
public class PatientDataController {

    private final PatientDataService patientDataService;

    public PatientDataController(PatientDataService patientDataService) {
        this.patientDataService = patientDataService;
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> findAllPatients(){
        List<PatientDTO> patients = patientDataService.findAll();
        if(patients.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(
            @PathVariable Long id,
            @RequestBody PatientDTO newPatientDTO) {
        return patientDataService.update(id, newPatientDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
