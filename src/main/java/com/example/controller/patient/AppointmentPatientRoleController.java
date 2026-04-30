package com.example.controller.patient;

import com.example.model.dto.appointment.request.CreateAppointmentRequest;
import com.example.model.dto.appointment.response.AppointmentFullInformationResponse;
import com.example.model.dto.appointment.response.AppointmentShortInformationResponse;
import com.example.model.dto.appointment.response.CreateAppointmentResponse;
import com.example.model.enums.StatusEnum;
import com.example.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/patient/appointments")
@Tag(name = "Записи")
public class AppointmentPatientRoleController {
    private final AppointmentService appointmentService;
    public AppointmentPatientRoleController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Operation(summary = "Создать запись")
    @SecurityRequirement(name = "bearer-jwt")
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

    @Operation(summary = "Получить все записи по ID пациента в виде краткой информации")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/information/short/{patientId}")
    public ResponseEntity<List<AppointmentShortInformationResponse>> findAllShortInfoByPatientId(@PathVariable long patientId) {
        List<AppointmentShortInformationResponse> appointments = appointmentService.findAllShortInfoByPatientId(patientId);
        if (appointments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(appointments);
    }

    @Operation(summary = "Получить полную информацию о записи")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/information/full/{id}")
    public ResponseEntity<AppointmentFullInformationResponse> findFullInfoById(@PathVariable long id) {
        return ResponseEntity.ok(appointmentService.findFullInfoById(id));
    }

    @Operation(summary = "Получить все записи по их статусу, в виде краткой информации")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/information/short/status/{status}")
    public ResponseEntity<List<AppointmentShortInformationResponse>> findAllShortInfoByStatus(@PathVariable StatusEnum status) {
        List<AppointmentShortInformationResponse> appointments = appointmentService.findAllShortInfoByStatus(status);
        if (appointments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(appointments);
    }
}
