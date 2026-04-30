package com.example.controller.patient;

import com.example.model.dto.SpecializationDTO;
import com.example.service.SpecializationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient/specializations")
@Tag(name = "Специализации")
public class SpecializationController {
    private final SpecializationService specializationService;
    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @Operation(summary = "Получить все специализации")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping
    public ResponseEntity<List<SpecializationDTO>> find(){
        List<SpecializationDTO> specializations = specializationService.findAll();

        if(specializations.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specializations);
    }
}
