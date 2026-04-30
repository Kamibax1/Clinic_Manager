package com.example.controller.doctor;

import com.example.exception.ResourceNotFoundException;
import com.example.model.dto.doctor.request.UpdateDoctorFullInformationRequest;
import com.example.model.dto.doctor.response.DoctorFullInformationResponse;
import com.example.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor/doctors")
@Tag(name = "Врачи")
public class DoctorDoctorRoleController {
    private final DoctorService doctorService;
    public DoctorDoctorRoleController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Operation(summary = "Получить врача по ID в виде полной информации")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/information/full/{id}")
    public ResponseEntity<DoctorFullInformationResponse> getFullInfoById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(doctorService.findFullInfoById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Изменить полную информацию врача")
    @SecurityRequirement(name = "bearer-jwt")
    @PutMapping("/information/full/{id}")
    public ResponseEntity<DoctorFullInformationResponse> updateFullInfo(
            @PathVariable long id,
            @RequestBody UpdateDoctorFullInformationRequest request
    ) {
        try {
            return ResponseEntity.ok(doctorService.updateFullInfo(id, request));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
