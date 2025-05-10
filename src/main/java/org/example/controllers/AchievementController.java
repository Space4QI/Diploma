package org.example.controllers;

import org.example.Dto.AchievementDTO;
import org.example.models.Achievement;
import org.example.services.AchievementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/achievements")
public class AchievementController {

    private final AchievementService achievementService;

    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping
    public ResponseEntity<List<AchievementDTO>> getAllAchievements() {
        return ResponseEntity.ok(achievementService.getAll());
    }

    @PostMapping
    public ResponseEntity<AchievementDTO> createAchievement(@RequestBody AchievementDTO dto) {
        return ResponseEntity.ok(achievementService.save(dto));
    }
}