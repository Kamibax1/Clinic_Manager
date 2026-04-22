package com.example.controller.admin;

import com.example.model.dto.appointment.response.AppointmentFullInformationResponse;
import com.example.model.enums.StatusEnum;
import com.example.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/appointments")
public class AppointmentAdminRoleController {
    private final AppointmentService appointmentService;
    public AppointmentAdminRoleController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
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

    @PutMapping("/information/full/{id}")
    public ResponseEntity<AppointmentFullInformationResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody StatusEnum status
    ) {
        return ResponseEntity.ok(appointmentService.updateStatus(id, status));
    }
}
