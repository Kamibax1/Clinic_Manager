package com.example.controller;

import com.example.model.dto.PatientShortInfoResponse;
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

    @GetMapping("/information/short")
    public ResponseEntity<List<PatientShortInfoResponse>> findAllShortInfo(){
        List<PatientShortInfoResponse> patients = patientService.findAllShortInfo();
        if(patients.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/information/short/{name}")
    public ResponseEntity<List<PatientShortInfoResponse>> findAllShortInfoByName(@PathVariable String name){
        List<PatientShortInfoResponse> patients = patientService.findAllShortInfoByName(name);
        if(patients.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patients);
    }
}
