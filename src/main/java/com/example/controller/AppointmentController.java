package com.example.controller;

import com.example.model.dto.*;
import com.example.model.enums.StatusEnum;
import com.example.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<CreateAppointmentRequest> save(@RequestBody CreateAppointmentRequest dto) {

        CreateAppointmentRequest created = appointmentService.createAppointment(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> findAll() {
        List<AppointmentDTO> list = appointmentService.findAll();

        if(list.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/full")
    public ResponseEntity<List<AppointmentFullDTO>> findAllFull() {
        List<AppointmentFullDTO> list = appointmentService.findAllFull();
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> findById(@PathVariable Long id) {
        return appointmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> update(@PathVariable Long id, @RequestBody AppointmentDTO appointmentDTO) {
        return appointmentService.update(id, appointmentDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if(appointmentService.existsById(id)){
            appointmentService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find/doctor/{id}")
    public ResponseEntity<List<AppointmentDTO>> findAllByDoctorId(@PathVariable Long id) {
        List<AppointmentDTO> appointments = appointmentService.findAllByDoctorId(id);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/find/status/{status}")
    public ResponseEntity<List<AppointmentDTO>> findAllByStatus(@PathVariable StatusEnum status) {
        List<AppointmentDTO> appointments = appointmentService.findAllByStatus(status);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/full/search/doctor/{doctorName}")
    public ResponseEntity<List<AppointmentFullDTO>> findAllByDoctorName(@PathVariable String doctorName) {
        List<AppointmentFullDTO> appointments = appointmentService.findAllByDoctorName(doctorName);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/information/{id}")
    public ResponseEntity<AppointmentInformationDTO> findInformationById(@PathVariable Long id) {
        return appointmentService.findAppointmentInformationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/information/{id}")
    public ResponseEntity<AppointmentInformationDTO> updateStatus(
            @PathVariable Long id,
            @RequestBody StatusEnum status
    ) {

        return appointmentService.updateStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
