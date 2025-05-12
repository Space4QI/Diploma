package org.example.controllers;

import org.example.Dto.TeamDTO;
import org.example.models.Team;
import org.example.services.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable UUID id) {
        return teamService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestBody TeamDTO dto) {
        return ResponseEntity.ok(teamService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable UUID id) {
        teamService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{teamId}/join/{userId}")
    public ResponseEntity<Void> joinTeam(@PathVariable UUID teamId, @PathVariable UUID userId) {
        teamService.joinTeam(teamId, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{teamId}/leave/{userId}")
    public ResponseEntity<Void> leaveTeam(@PathVariable UUID userId) {
        teamService.leaveTeam(userId);
        return ResponseEntity.noContent().build();
    }
}