package com.example.controller.patient;

import com.example.model.dto.SpecializationDTO;
import com.example.service.SpecializationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient/specializations")
public class SpecializationController {
    private final SpecializationService specializationService;
    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @GetMapping
    public ResponseEntity<List<SpecializationDTO>> findAllSpecialization(){
        List<SpecializationDTO> specializations = specializationService.findAllSpecializations();

        if(specializations.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specializations);
    }
}
