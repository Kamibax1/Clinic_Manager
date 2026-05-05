package com.example.controller.doctor;

import com.example.model.dto.appointment.request.UpdateAppointmentStatusRequest;
import com.example.model.dto.appointment.request.UpdateAppointmentSymptomsRequest;
import com.example.model.dto.appointment.response.AppointmentFullInformationResponse;
import com.example.model.dto.appointment.response.AppointmentShortInformationResponse;
import com.example.model.enums.StatusEnum;
import com.example.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor/appointments")
@Tag(name = "Записи")
public class AppointmentDoctorRoleController {
    private final AppointmentService appointmentService;
    public AppointmentDoctorRoleController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Operation(summary = "Получить все записи в виде краткой информации")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/information/short")
    public ResponseEntity<List<AppointmentShortInformationResponse>> findAllShortInfo() {
        List<AppointmentShortInformationResponse> appointments = appointmentService.findAllShortInfo();
        if (appointments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(appointments);
    }

    @Operation(summary = "Получить все записи по части ФИО врача в виде краткой информации")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/information/short/doctor/{partDoctorName}")
    public ResponseEntity<List<AppointmentShortInformationResponse>> findAllShortInfoByDoctorName(@PathVariable String partDoctorName) {
        List<AppointmentShortInformationResponse> appointments = appointmentService.findAllShortInfoByDoctorName(partDoctorName);
        if (appointments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(appointments);
    }

    @Operation(summary = "Получить запись по ID в виде краткой информации")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/information/short/{id}")
    public ResponseEntity<List<AppointmentShortInformationResponse>> findShortInfoById(@PathVariable long id) {
        List<AppointmentShortInformationResponse> appointments = appointmentService.findAllShortInfoByDoctorId(id);

        if (appointments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(appointments);
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

    @Operation(summary = "Изменить статус записи, к которой привязан определенный врач")
    @SecurityRequirement(name = "bearer-jwt")
    @PutMapping("/information/full/status/{id}")
    public ResponseEntity<AppointmentFullInformationResponse> updateDoctorAppointmentStatus(
            @PathVariable Long id,
            @RequestBody UpdateAppointmentStatusRequest request
            ) {
        return ResponseEntity.ok(appointmentService.updateDoctorAppointmentStatus(id, request));
    }

    @Operation(summary = "Изменить симптомы записи, к которой привязан определенный врач")
    @SecurityRequirement(name = "bearer-jwt")
    @PutMapping("/information/full/symptoms/{id}")
    public ResponseEntity<AppointmentFullInformationResponse> updateDoctorAppointmentSymptoms(
            @PathVariable Long id,
            @RequestBody UpdateAppointmentSymptomsRequest request
    ) {
        return ResponseEntity.ok(appointmentService.updateDoctorAppointmentSymptoms(id, request));
    }
}
