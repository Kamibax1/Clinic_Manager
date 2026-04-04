package com.example.controller;

import com.example.model.dto.DoctorDTO;
import com.example.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> findAll(){
        List<DoctorDTO> doctors = doctorService.findAll();
        if(doctors.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> findById(@PathVariable long id) {
        return doctorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/specializations")
    public ResponseEntity<List<String>> findAllSpecialization(){
        List<String> specializations = doctorService.findAllSpecializations();

        if(specializations.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specializations);
    }
}
