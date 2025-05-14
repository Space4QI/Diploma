package org.example.controllers;

import org.example.Dto.AchievementDTO;
import org.example.services.AchievementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/achievements")
public class AchievementController {

    private static final Logger logger = LoggerFactory.getLogger(AchievementController.class);

    private final AchievementService achievementService;

    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping
    public ResponseEntity<List<AchievementDTO>> getAllAchievements() {
        logger.info("[GET /achievements] getAllAchievements called");
        List<AchievementDTO> achievements = achievementService.getAll();
        logger.info("Found {} achievements", achievements.size());
        return ResponseEntity.ok(achievements);
    }

    @PostMapping
    public ResponseEntity<AchievementDTO> createAchievement(@RequestBody AchievementDTO dto) {
        logger.info("[POST /achievements] createAchievement called with payload: {}", dto);
        AchievementDTO saved = achievementService.save(dto);
        logger.info("Achievement created: {}", saved);
        return ResponseEntity.ok(saved);
    }
}
