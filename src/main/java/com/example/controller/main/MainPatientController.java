package com.example.controller.main;

import com.example.model.dto.DoctorDTO;
import com.example.service.main.MainPatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/main/patient")
public class MainPatientController {

    private final MainPatientService mainService;

    public MainPatientController(MainPatientService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDTO>> findAllDoctors(){
        List<DoctorDTO> doctors = mainService.findAllDoctors();
        if(doctors.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/specializations")
    public ResponseEntity<List<String>> findAllSpecialization(){
        List<String> specializations = mainService.findAllSpecializations();

        if(specializations.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specializations);
    }
}
