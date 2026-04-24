package com.example.controller.patient;

import com.example.model.dto.appointment.request.CreateAppointmentRequest;
import com.example.model.dto.appointment.response.AppointmentFullInformationResponse;
import com.example.model.dto.appointment.response.AppointmentShortInfoResponse;
import com.example.model.dto.appointment.response.CreateAppointmentResponse;
import com.example.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/patient/appointments")
public class AppointmentPatientRoleController {
    private final AppointmentService appointmentService;
    public AppointmentPatientRoleController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<CreateAppointmentResponse> save(@Valid @RequestBody CreateAppointmentRequest dto) {

        CreateAppointmentResponse created = appointmentService.createAppointment(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/information/short/{patientId}")
    public ResponseEntity<List<AppointmentShortInfoResponse>> findAllShortInfoByPatientId(@PathVariable long patientId) {
        List<AppointmentShortInfoResponse> appointments = appointmentService.findAllShortInfoByPatientId(patientId);
        if (appointments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/information/full/{id}")
    public ResponseEntity<AppointmentFullInformationResponse> findFullInfoById(@PathVariable long id) {
        return ResponseEntity.ok(appointmentService.findFullInfoById(id));
    }
}
