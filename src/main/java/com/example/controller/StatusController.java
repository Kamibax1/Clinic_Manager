package com.example.controller;

import com.example.model.dto.StatusDTO;
import com.example.model.enums.StatusEnum;
import com.example.service.StatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status")
public class StatusController {
    private final StatusService statusService;

    public StatusController(StatusService statusService) {
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

    @GetMapping("/{id}")
    public ResponseEntity<StatusDTO> findById(@PathVariable long id) {
        return statusService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<StatusDTO> findByStatus(@PathVariable StatusEnum name) {
        StatusDTO dto = statusService.findByStatus(name);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}
