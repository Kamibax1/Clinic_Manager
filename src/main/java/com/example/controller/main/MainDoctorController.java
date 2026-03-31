package com.example.controller.main;

import com.example.model.dto.AppointmentDTO;
import com.example.model.dto.DoctorDTO;
import com.example.model.dto.PatientDTO;
import com.example.model.enums.StatusEnum;
import com.example.service.main.MainDoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/main/doctor")
public class MainDoctorController {
    private final MainDoctorService mainDoctorService;
    public MainDoctorController(MainDoctorService mainDoctorService) {
        this.mainDoctorService = mainDoctorService;
    }

    @GetMapping("/patients/{partFullName}")
    public ResponseEntity<List<PatientDTO>> searchPatientsByPartFullName(@PathVariable String partFullName){
        return mainDoctorService.findPatientsByPartFullName(partFullName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/patients")
    public ResponseEntity<List<PatientDTO>> findAllPatients(){
        List<PatientDTO> patients = mainDoctorService.findAllPatients();
        if(patients.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<List<AppointmentDTO>> findAllAppointmentByDoctorId(@PathVariable Long id){
        return mainDoctorService.findAllAppointmentByDoctorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/appointments/{status}")
    public ResponseEntity<List<AppointmentDTO>> findAllAppointmentByStatus(@PathVariable StatusEnum status){
        return mainDoctorService.findAllAppointmentByStatus(status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDTO>> findAllDoctors(){
        List<DoctorDTO> doctors = mainDoctorService.findAllDoctors();
        if(doctors.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctors);
    }
}
