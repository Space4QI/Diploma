package org.example.controllers;

import org.example.services.AnalyticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AdminStatsController.class);

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
            logger.debug("Parameter 'from' not provided, defaulting to {}", from);
        }
        if (to == null) {
            to = LocalDate.now();
            logger.debug("Parameter 'to' not provided, defaulting to {}", to);
        }

        logger.info("[GET /admin/stats] showStatistics called with from={} to={}", from, to);

        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("topUsers", analyticsService.getTopUsersBetween(from, to));
        model.addAttribute("popularEvents", analyticsService.getPopularEventsBetween(from, to));
        model.addAttribute("eventCounts", analyticsService.getEventCountByDateBetween(from, to));
        model.addAttribute("topTeams", analyticsService.getTopTeams());
        model.addAttribute("participationLog", analyticsService.getParticipationLog());
        model.addAttribute("ecoHero", analyticsService.getEcoHeroOfTheWeek());

        logger.info("Statistics model populated for date range [{} - {}]", from, to);
        return "stats";
    }
}
