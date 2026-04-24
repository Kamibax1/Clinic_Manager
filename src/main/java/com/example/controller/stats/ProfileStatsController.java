package com.example.controller.stats;

import com.example.model.dto.ProfileStatsResponse;
import com.example.service.ProfileStatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProfileStatsController {
    private final ProfileStatsService profileStatsService;
    public ProfileStatsController(ProfileStatsService profileStatsService) {
        this.profileStatsService = profileStatsService;
    }

    @GetMapping("/patient/profile/stats/{id}")
    public ResponseEntity<ProfileStatsResponse> getProfileStatsPatient(@PathVariable long id) {
        return ResponseEntity.ok(profileStatsService.getProfileStatsPatient(id));
    }

    @GetMapping("/doctor/profile/stats/{id}")
    public ResponseEntity<ProfileStatsResponse> getProfileStatsDoctor(@PathVariable long id) {
        return ResponseEntity.ok(profileStatsService.getProfileStatsDoctor(id));
    }
}
