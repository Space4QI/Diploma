package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Teams", description = "Operations related to team management and participation")
public class TeamController {

    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    private final TeamService teamService;
    private final UserService userService;

    public TeamController(TeamService teamService, UserService userService) {
        this.teamService = teamService;
        this.userService = userService;
    }

    @GetMapping
    @Operation(
            summary = "Get all teams",
            description = "Returns a list of all teams",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of teams",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TeamDTO.class)))
            }
    )
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        logger.info("GET /teams - getAllTeams called");
        return ResponseEntity.ok(teamService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get team by ID",
            description = "Returns a team by its UUID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Team found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TeamDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Team not found")
            }
    )
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
    @Operation(
            summary = "Create a new team",
            description = "Creates a new team and returns the saved object",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Team created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TeamDTO.class)))
            }
    )
    public ResponseEntity<TeamDTO> createTeam(@RequestBody TeamDTO dto) {
        logger.info("POST /teams - createTeam called with payload: {}", dto);
        return ResponseEntity.ok(teamService.save(dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a team",
            description = "Deletes a team by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Team deleted successfully")
            }
    )
    public ResponseEntity<Void> deleteTeam(@PathVariable UUID id) {
        logger.info("DELETE /teams/{} - deleteTeam called", id);
        teamService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{teamId}/join/{userId}")
    @Operation(
            summary = "Join a team",
            description = "Adds a user to the specified team",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User joined team successfully")
            }
    )
    public ResponseEntity<Void> joinTeam(@PathVariable UUID teamId, @PathVariable UUID userId) {
        logger.info("PUT /teams/{}/join/{} - joinTeam called", teamId, userId);
        teamService.joinTeam(teamId, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{teamId}/leave/{userId}")
    @Operation(
            summary = "Leave a team",
            description = "Removes a user from their team",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User left team successfully")
            }
    )
    public ResponseEntity<Void> leaveTeam(@PathVariable UUID userId) {
        logger.info("PUT /teams/.../leave/{} - leaveTeam called", userId);
        teamService.leaveTeam(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{teamId}/users")
    @Operation(
            summary = "Get users by team ID",
            description = "Returns a list of users who are members of the given team",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of users in team",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class)))
            }
    )
    public ResponseEntity<List<UserDTO>> getUsersByTeam(@PathVariable UUID teamId) {
        logger.info("GET /teams/{}/users - getUsersByTeam called", teamId);
        List<UserDTO> users = userService.getUsersByTeam(teamId);
        logger.info("Found {} users for team {}", users.size(), teamId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/top/week")
    @Operation(
            summary = "Get top teams of the week",
            description = "Returns a list of top-performing teams for the current week",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Top teams of the week",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TeamTopDTO.class)))
            }
    )
    public List<TeamTopDTO> getTopTeamsWeek() {
        LocalDateTime now = LocalDateTime.now();
        return teamService.getTopTeamsBetween(now.minusWeeks(1), now);
    }

    @GetMapping("/top/month")
    @Operation(
            summary = "Get top teams of the month",
            description = "Returns a list of top-performing teams for the current month",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Top teams of the month",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TeamTopDTO.class)))
            }
    )
    public List<TeamTopDTO> getTopTeamsMonth() {
        LocalDateTime now = LocalDateTime.now();
        return teamService.getTopTeamsBetween(now.minusMonths(1), now);
    }

    @GetMapping("/top/all")
    @Operation(
            summary = "Get all-time top teams",
            description = "Returns a list of all-time best performing teams",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Top teams of all time",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TeamTopDTO.class)))
            }
    )
    public List<TeamTopDTO> getTopTeamsAllTime() {
        return teamService.getTopTeamsBetween(LocalDateTime.MIN, LocalDateTime.now());
    }
}
