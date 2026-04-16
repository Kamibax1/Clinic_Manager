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

    @GetMapping("/information/short")
    public ResponseEntity<List<AppointmentShortInfoResponse>> findAllShortInfo() {
        List<AppointmentShortInfoResponse> appointments = appointmentService.findAllShortInfo();
        if (appointments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/information/short/{name}")
    public ResponseEntity<List<AppointmentShortInfoResponse>> findAllShortInfoByDoctorName(@PathVariable String name) {
        List<AppointmentShortInfoResponse> appointments = appointmentService.findAllShortInfoByDoctorName(name);
        if (appointments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("information/full/{id}")
    public ResponseEntity<AppointmentFullInformationResponse> findFullInfoById(@PathVariable long id) {
        return appointmentService.findFullInfoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<AppointmentDTO> update(@PathVariable Long id, @RequestBody AppointmentDTO appointmentDTO) {
//        return appointmentService.update(id, appointmentDTO)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

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

    @PutMapping("/information/{id}")
    public ResponseEntity<AppointmentFullInformationResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody StatusEnum status
    ) {

        return appointmentService.updateStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
