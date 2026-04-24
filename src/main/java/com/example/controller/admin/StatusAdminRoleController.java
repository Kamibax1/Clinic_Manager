package com.example.controller.admin;

import com.example.model.dto.StatusDTO;
import com.example.model.enums.StatusEnum;
import com.example.service.StatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/status")
public class StatusAdminRoleController {
    private final StatusService statusService;

    public StatusAdminRoleController(StatusService statusService) {
        this.statusService = statusService;
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
