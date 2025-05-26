package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.Dto.ActivityDTO;
import org.example.services.FeedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
@Tag(name = "Feed", description = "Recent platform activity feed")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/feed")
    @Operation(
            summary = "Get recent activities",
            description = "Returns a list of recent user and event activities on the platform",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of recent activities",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ActivityDTO.class)))
            }
    )
    public List<ActivityDTO> getRecent() {
        return feedService.getRecentActivities();
    }
}
