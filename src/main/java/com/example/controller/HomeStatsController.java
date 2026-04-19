package com.example.controller;

import com.example.model.dto.HomeStatsResponse;
import com.example.service.HomeStatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home")
public class HomeStatsController {
    private final HomeStatsService homeStatsService;
    public HomeStatsController(HomeStatsService homeStatsService) {
        this.homeStatsService = homeStatsService;
    }

    @GetMapping("/stats")
    public ResponseEntity<HomeStatsResponse> getHomeStats() {
        return ResponseEntity.ok(homeStatsService.getHomeStats());
    }
}
