package org.example.controllers;

import org.example.Dto.TeamDTO;
import org.example.Dto.TeamTopDTO;
import org.example.Dto.UserDTO;
import org.example.services.TeamService;
import org.example.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    private final TeamService teamService;
    private final UserService userService;

    public TeamController(TeamService teamService, UserService userService) {
        this.teamService = teamService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        logger.info("GET /teams - getAllTeams called");
        return ResponseEntity.ok(teamService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable UUID id) {
        logger.info("GET /teams/{} - getTeamById called", id);
        return teamService.getById(id)
                .map(team -> {
                    logger.info("Team found: {}", team);
                    return ResponseEntity.ok(team);
                })
                .orElseGet(() -> {
                    logger.warn("Team with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestBody TeamDTO dto) {
        logger.info("POST /teams - createTeam called with payload: {}", dto);
        return ResponseEntity.ok(teamService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable UUID id) {
        logger.info("DELETE /teams/{} - deleteTeam called", id);
        teamService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{teamId}/join/{userId}")
    public ResponseEntity<Void> joinTeam(@PathVariable UUID teamId, @PathVariable UUID userId) {
        logger.info("PUT /teams/{}/join/{} - joinTeam called", teamId, userId);
        teamService.joinTeam(teamId, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{teamId}/leave/{userId}")
    public ResponseEntity<Void> leaveTeam(@PathVariable UUID userId) {
        logger.info("PUT /teams/.../leave/{} - leaveTeam called", userId);
        teamService.leaveTeam(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{teamId}/users")
    public ResponseEntity<List<UserDTO>> getUsersByTeam(@PathVariable UUID teamId) {
        logger.info("GET /teams/{}/users - getUsersByTeam called", teamId);
        List<UserDTO> users = userService.getUsersByTeam(teamId);
        logger.info("Found {} users for team {}", users.size(), teamId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/top/week")
    public List<TeamTopDTO> getTopTeamsWeek() {
        LocalDateTime now = LocalDateTime.now();
        return teamService.getTopTeamsBetween(now.minusWeeks(1), now);
    }

    @GetMapping("/top/month")
    public List<TeamTopDTO> getTopTeamsMonth() {
        LocalDateTime now = LocalDateTime.now();
        return teamService.getTopTeamsBetween(now.minusMonths(1), now);
    }

    @GetMapping("/top/all")
    public List<TeamTopDTO> getTopTeamsAllTime() {
        return teamService.getTopTeamsBetween(LocalDateTime.MIN, LocalDateTime.now());
    }

}
