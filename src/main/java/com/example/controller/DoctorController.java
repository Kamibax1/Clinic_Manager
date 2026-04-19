package com.example.controller;

import com.example.model.dto.DoctorShortInfoResponse;
import com.example.model.dto.SpecializationDTO;
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
