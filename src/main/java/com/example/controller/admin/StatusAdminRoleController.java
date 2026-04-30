package com.example.controller.admin;

import com.example.model.dto.StatusDTO;
import com.example.model.enums.StatusEnum;
import com.example.service.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/status")
@Tag(name = "Статусы")
public class StatusAdminRoleController {
    private final StatusService statusService;

    public StatusAdminRoleController(StatusService statusService) {
        this.statusService = statusService;
    }

    @Operation(summary = "Получить статус по ID")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<StatusDTO> findById(@PathVariable long id) {
        return statusService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получить статус по названию")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search/name/{name}")
    public ResponseEntity<StatusDTO> findByStatus(@PathVariable StatusEnum name) {
        StatusDTO dto = statusService.findByStatus(name);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}
