package org.example.controllers;

import org.example.Dto.ActivityDTO;
import org.example.services.FeedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/recent")
    public List<ActivityDTO> getRecent(@RequestParam UUID userId) {
        return feedService.getRecentActivities(userId);
    }
}