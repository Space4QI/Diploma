package org.example.controllers;

import org.example.Dto.ActivityDTO;
import org.example.services.FeedService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/feed")
    public List<ActivityDTO> getRecent() {
        return feedService.getRecentActivities();
    }
}