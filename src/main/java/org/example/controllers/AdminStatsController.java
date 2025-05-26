package org.example.controllers;

import org.example.services.AnalyticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/stats")
public class AdminStatsController {

    private final AnalyticsService analyticsService;

    public AdminStatsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping
    public String showStatistics(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            Model model
    ) {
        if (from == null) {
            from = LocalDate.now().minusDays(30);
        }
        if (to == null) {
            to = LocalDate.now();
        }

        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("topUsers", analyticsService.getTopUsersBetween(from, to));
        model.addAttribute("popularEvents", analyticsService.getPopularEventsBetween(from, to));
        model.addAttribute("eventCounts", analyticsService.getEventCountByDateBetween(from, to));
        model.addAttribute("topTeams", analyticsService.getTopTeams());
        model.addAttribute("participationLog", analyticsService.getParticipationLog());
        model.addAttribute("ecoHero", analyticsService.getEcoHeroOfTheWeek());

        return "stats";
    }


}

