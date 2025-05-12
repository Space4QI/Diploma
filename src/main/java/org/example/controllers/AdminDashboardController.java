package org.example.controllers;

import org.example.services.AdminService;
import org.example.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final AdminService adminService;
    private final UserService userService;

    public AdminDashboardController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("teams", adminService.getAllTeams());
        model.addAttribute("events", adminService.getEventsToVerify());
        model.addAttribute("users", userService.getAll()); // <-- добавь это
        return "admin";
    }


    @PostMapping("/add-points")
    public String addPoints(@RequestParam UUID teamId,
                            @RequestParam int points) {
        adminService.addPointsToTeam(teamId, points);
        return "redirect:/admin";
    }

    @PostMapping("/calculate-top")
    public String calculateTop(Model model) {
        model.addAttribute("sortedTeams", adminService.getSortedTeams());
        model.addAttribute("teams", adminService.getAllTeams());
        model.addAttribute("events", adminService.getEventsToVerify());
        return "admin";
    }

    @PostMapping("/verify-event")
    public String verifyEvent(@RequestParam UUID eventId,
                              @RequestParam int teamPoints,
                              @RequestParam(required = false) String comment) {
        adminService.verifyEvent(eventId, teamPoints, comment);
        return "redirect:/admin";
    }

    @PostMapping("/reject-event")
    public String rejectEvent(@RequestParam UUID eventId,
                              @RequestParam(required = false) String comment) {
        adminService.rejectEvent(eventId, comment);
        return "redirect:/admin";
    }

//    @GetMapping("/manage-roles")
//    public String manageRolesPage(Model model) {
//        model.addAttribute("users", userService.getAll());
//        model.addAttribute("availableRoles", List.of("USER", "ORGANIZER", "ADMIN"));
//        return "admin-manage-roles";
//    }

    @PostMapping("/assign-role")
    public String assignRole(@RequestParam UUID userId,
                             @RequestParam String role) {
        userService.assignRole(userId, role);
        return "redirect:/admin";
    }

    @PostMapping("/add-points-to-participant")
    public String addPointsToParticipant(@RequestParam UUID eventId,
                                         @RequestParam String participantName,
                                         @RequestParam int points) {
        adminService.addPointsToParticipant(eventId, participantName, points);
        return "redirect:/admin";
    }
}
