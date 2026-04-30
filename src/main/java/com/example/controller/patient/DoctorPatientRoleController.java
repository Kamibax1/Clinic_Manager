package com.example.controller.patient;

import com.example.model.dto.doctor.response.DoctorShortInfoResponse;
import com.example.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/patient/doctors")
@Tag(name = "Врачи")
public class DoctorPatientRoleController {
    private final DoctorService doctorService;
    public DoctorPatientRoleController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Operation(summary = "Получить всех врачей в виде краткой информации")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/information/short")
    public ResponseEntity<List<DoctorShortInfoResponse>> findAllShortInfo(){
        List<DoctorShortInfoResponse> doctors = doctorService.findAllShortInfo();
        if(doctors.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctors);
    }

    @Operation(summary = "Получить всех врачей по их части ФИО в виде краткой информации")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/information/short/name/{name}")
    public ResponseEntity<List<DoctorShortInfoResponse>> findAllShortInfoByName(@PathVariable String name){
        List<DoctorShortInfoResponse> doctors = doctorService.findAllShortInfoByName(name);
        if(doctors.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctors);
    }

    @Operation(summary = "Получить всех врачей по их специализации в виде краткой информации")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/information/short/specialization/{specialization}")
    public ResponseEntity<List<DoctorShortInfoResponse>> findAllShortInfoBySpecialization(@PathVariable String specialization){
        List<DoctorShortInfoResponse> doctors = doctorService.findAllShortInfoBySpecialization(specialization);
        if(doctors.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctors);
    }
}
