package com.example.controller;

import com.example.model.dto.PatientDTO;
import com.example.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientDataService) {
        this.patientService = patientDataService;
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> findAll(){
        List<PatientDTO> patients = patientService.findAll();
        if(patients.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> findById(@PathVariable long id) {
        return patientService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/{partFullName}")
    public ResponseEntity<List<PatientDTO>> findByPartFullName(@PathVariable String partFullName){
        List<PatientDTO> patients = patientService.findByPartFullName(partFullName);
        return ResponseEntity.ok(patients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> update(
            @PathVariable Long id,
            @RequestBody PatientDTO newPatientDTO) {
        return patientService.update(id, newPatientDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
