package org.example.controllers;

import org.example.services.AdminService;
import org.example.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private static final Logger logger = LoggerFactory.getLogger(AdminDashboardController.class);

    private final AdminService adminService;
    private final UserService userService;

    public AdminDashboardController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping
    public String adminPage(Model model) {
        logger.info("[GET /admin] adminPage called");
        model.addAttribute("teams", adminService.getAllTeams());
        model.addAttribute("events", adminService.getEventsToVerify());
        model.addAttribute("users", userService.getAll());
        System.out.println(">>> ADMIN PAGE RENDERED");
        logger.info("Admin dashboard model populated with teams, events, users");
        return "admin";
    }

    @PostMapping("/add-points-to-team")
    public String addPoints(@RequestParam UUID teamId,
                            @RequestParam int points) {
        System.out.println(">>> ADD POINTS TRIGGERED");
        logger.info("[POST /admin/add-points] teamId={}, points={}", teamId, points);
        adminService.addPointsToTeam(teamId, points);
        return "redirect:/admin";
    }

    @PostMapping("/add-points-to-user")
    public String addPointsToUser(@RequestParam UUID userId,
                                  @RequestParam int points) {
        logger.info("[POST /admin/add-points-to-user] userId={}, points={}", userId, points);
        adminService.addPointsToUser(userId, points);
        return "redirect:/admin";
    }


    @PostMapping("/calculate-top")
    public String calculateTop(Model model) {
        logger.info("[POST /admin/calculate-top] calculateTop called");
        model.addAttribute("sortedTeams", adminService.getSortedTeams());
        model.addAttribute("teams", adminService.getAllTeams());
        model.addAttribute("events", adminService.getEventsToVerify());
        logger.info("Top teams calculated and model updated");
        return "admin";
    }

    @PostMapping("/verify-event")
    public String verifyEvent(@RequestParam UUID eventId,
                              @RequestParam int teamPoints,
                              @RequestParam(required = false) String comment) {
        logger.info("[POST /admin/verify-event] eventId={}, teamPoints={}, comment={}", eventId, teamPoints, comment);
        adminService.verifyEvent(eventId, teamPoints, comment);
        return "redirect:/admin";
    }

    @PostMapping("/reject-event")
    public String rejectEvent(@RequestParam UUID eventId,
                              @RequestParam(required = false) String comment) {
        logger.info("[POST /admin/reject-event] eventId={}, comment={}", eventId, comment);
        adminService.rejectEvent(eventId, comment);
        return "redirect:/admin";
    }

    @PostMapping("/assign-role")
    public String assignRole(@RequestParam UUID userId,
                             @RequestParam String role) {
        logger.info("[POST /admin/assign-role] userId={}, role={}", userId, role);
        userService.assignRole(userId, role);
        return "redirect:/admin";
    }

    @PostMapping("/add-points-to-participant")
    public String addPointsToParticipant(@RequestParam UUID eventId,
                                         @RequestParam String participantName,
                                         @RequestParam int points) {
        logger.info("[POST /admin/add-points-to-participant] eventId={}, participant={}, points={}", eventId, participantName, points);
        adminService.addPointsToParticipant(eventId, participantName, points);
        return "redirect:/admin";
    }
}
