package com.example.controller.doctor;

import com.example.model.dto.patient.response.PatientShortInfoResponse;
import com.example.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor/patients")
public class PatientDoctorRoleController {
    private final PatientService patientService;
    public PatientDoctorRoleController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/information/short")
    public ResponseEntity<List<PatientShortInfoResponse>> findAllPatientShortInfo() {
        List<PatientShortInfoResponse> patients = patientService.findAllShortInfo();
        if (patients.isEmpty()) {
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
