package com.example.controller;

import com.example.model.dto.DoctorDTO;
import com.example.service.MainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/main")
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService) {
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
