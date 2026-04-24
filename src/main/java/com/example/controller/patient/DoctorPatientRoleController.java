package com.example.controller.patient;

import com.example.model.dto.doctor.response.DoctorShortInfoResponse;
import com.example.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/patient/doctors")
public class DoctorPatientRoleController {
    private final DoctorService doctorService;
    public DoctorPatientRoleController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/information/short")
    public ResponseEntity<List<DoctorShortInfoResponse>> findAllShortInfo(){
        List<DoctorShortInfoResponse> doctors = doctorService.findAllShortInfo();
        if(doctors.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/information/short/{name}")
    public ResponseEntity<List<DoctorShortInfoResponse>> findAllShortInfoByName(@PathVariable String name){
        List<DoctorShortInfoResponse> doctors = doctorService.findAllShortInfoByName(name);
        if(doctors.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctors);
    }
}
