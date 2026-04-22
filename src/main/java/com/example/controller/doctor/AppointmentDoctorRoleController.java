package com.example.controller.doctor;

import com.example.model.dto.appointment.request.UpdateAppointmentStatusRequest;
import com.example.model.dto.appointment.response.AppointmentFullInformationResponse;
import com.example.model.dto.appointment.response.AppointmentShortInfoResponse;
import com.example.model.enums.StatusEnum;
import com.example.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor/appointments")
public class AppointmentDoctorRoleController {
    private final AppointmentService appointmentService;
    public AppointmentDoctorRoleController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
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

    @GetMapping("/information/short/{id}")
    public ResponseEntity<List<AppointmentShortInfoResponse>> findShortInfoById(@PathVariable long id) {
        List<AppointmentShortInfoResponse> appointments = appointmentService.findAllShortInfoByDoctorId(id);

        if (appointments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/information/{id}")
    public ResponseEntity<AppointmentFullInformationResponse> updateDoctorAppointmentStatus(
            @PathVariable Long id,
            @RequestBody UpdateAppointmentStatusRequest request
            ) {
        return ResponseEntity.ok(appointmentService.updateDoctorAppointmentStatus(id, request));
    }
}
