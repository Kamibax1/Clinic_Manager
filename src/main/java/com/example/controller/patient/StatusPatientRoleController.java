package com.example.controller.patient;

import com.example.model.dto.StatusDTO;
import com.example.service.StatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/patient/status")
public class StatusPatientRoleController {
    private final StatusService statusService;
    public StatusPatientRoleController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping
    public ResponseEntity<List<StatusDTO>> findAll(){
        List<StatusDTO> statusEntities = statusService.findAll();
        if(statusEntities.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(statusEntities);
    }
}
