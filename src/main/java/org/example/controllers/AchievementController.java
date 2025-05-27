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
import java.util.UUID;

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

    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Get achievements for a specific user",
            description = "Returns all achievements earned by a user with given ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AchievementDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    public ResponseEntity<List<AchievementDTO>> getAchievementsByUserId(@PathVariable UUID userId) {
        logger.info("[GET /achievements/user/{}] getAchievementsByUser called", userId);
        List<AchievementDTO> userAchievements = achievementService.getByUserId(userId);
        logger.info("Found {} achievements for user {}", userAchievements.size(), userId);
        return ResponseEntity.ok(userAchievements);
    }

    @GetMapping("/phone/{phone}")
    @Operation(
            summary = "Get achievements for a user by phone",
            description = "Returns all achievements earned by a user with given phone number",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AchievementDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    public ResponseEntity<List<AchievementDTO>> getAchievementsByUserPhone(@PathVariable String phone) {
        logger.info("[GET /achievements/phone/{}] getAchievementsByUserPhone called", phone);
        List<AchievementDTO> userAchievements = achievementService.getByUserPhone(phone);
        logger.info("Found {} achievements for user with phone {}", userAchievements.size(), phone);
        return ResponseEntity.ok(userAchievements);
    }


}
