package com.example.controller.stats;

import com.example.model.dto.stats.ProfileStatsResponse;
import com.example.service.ProfileStatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Статистики")
public class ProfileStatsController {
    private final ProfileStatsService profileStatsService;
    public ProfileStatsController(ProfileStatsService profileStatsService) {
        this.profileStatsService = profileStatsService;
    }

    @Operation(summary = "Получить статистику на экране профиля для пациента")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/patient/profile/stats/{id}")
    public ResponseEntity<ProfileStatsResponse> getProfileStatsPatient(@PathVariable long id) {
        return ResponseEntity.ok(profileStatsService.getProfileStatsPatient(id));
    }

    @Operation(summary = "Получить статистику на экране профиля для врача")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/doctor/profile/stats/{id}")
    public ResponseEntity<ProfileStatsResponse> getProfileStatsDoctor(@PathVariable long id) {
        return ResponseEntity.ok(profileStatsService.getProfileStatsDoctor(id));
    }
}
