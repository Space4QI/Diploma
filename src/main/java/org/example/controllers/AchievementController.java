package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.Dto.AchievementDTO;
import org.example.services.AchievementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/achievements")
@Tag(name = "Achievements", description = "API for managing user achievements")
public class AchievementController {

    private static final Logger logger = LoggerFactory.getLogger(AchievementController.class);
    private final AchievementService achievementService;

    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping
    @Operation(
            summary = "Get all achievements",
            description = "Returns a list of all available achievements",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AchievementDTO.class)))
            }
    )
    public ResponseEntity<List<AchievementDTO>> getAllAchievements() {
        logger.info("[GET /achievements] getAllAchievements called");
        List<AchievementDTO> achievements = achievementService.getAll();
        logger.info("Found {} achievements", achievements.size());
        return ResponseEntity.ok(achievements);
    }

    @PostMapping
    @Operation(
            summary = "Create a new achievement",
            description = "Creates a new achievement and returns the saved object",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Achievement created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AchievementDTO.class)))
            }
    )
    public ResponseEntity<AchievementDTO> createAchievement(@RequestBody AchievementDTO dto) {
        logger.info("[POST /achievements] createAchievement called with payload: {}", dto);
        AchievementDTO saved = achievementService.save(dto);
        logger.info("Achievement created: {}", saved);
        return ResponseEntity.ok(saved);
    }
}
