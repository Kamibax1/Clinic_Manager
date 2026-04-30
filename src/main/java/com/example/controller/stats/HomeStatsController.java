package com.example.controller.stats;

import com.example.model.dto.stats.HomeStatsResponse;
import com.example.service.HomeStatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor/home")
@Tag(name = "Статистики")
public class HomeStatsController {
    private final HomeStatsService homeStatsService;
    public HomeStatsController(HomeStatsService homeStatsService) {
        this.homeStatsService = homeStatsService;
    }

    @Operation(summary = "Получить статистику на главном экране")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/stats")
    public ResponseEntity<HomeStatsResponse> getHomeStats() {
        return ResponseEntity.ok(homeStatsService.getHomeStats());
    }
}
