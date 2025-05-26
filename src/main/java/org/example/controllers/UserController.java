package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.Dto.EcoHeroDTO;
import org.example.Dto.UserDTO;
import org.example.Dto.UserTopDTO;
import org.example.services.AnalyticsService;
import org.example.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Operations related to user management and ranking")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final AnalyticsService analyticsService;

    public UserController(UserService userService, AnalyticsService analyticsService) {
        this.userService = userService;
        this.analyticsService = analyticsService;
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a list of all users",
            responses = @ApiResponse(responseCode = "200", description = "List of users",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))))
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("GET /users - getAllUsers called");
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Returns a user by their UUID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        logger.info("GET /users/{} - getUserById called", id);
        return userService.getById(id)
                .map(user -> {
                    logger.info("User found: {}", user);
                    return ResponseEntity.ok(user);
                })
                .orElseGet(() -> {
                    logger.warn("User with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/phone/{phone}")
    @Operation(summary = "Get user by phone", description = "Returns a user by their phone number",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    public ResponseEntity<UserDTO> getUserByPhone(@PathVariable String phone) {
        logger.info("GET /users/phone/{} - getUserByPhone called", phone);
        return userService.getByPhone(phone)
                .map(user -> {
                    logger.info("User found: {}", user);
                    return ResponseEntity.ok(user);
                })
                .orElseGet(() -> {
                    logger.warn("User with phone {} not found", phone);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    @Operation(summary = "Create a user", description = "Creates a new user",
            responses = @ApiResponse(responseCode = "200", description = "User created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))))
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto) {
        logger.info("POST /users - createUser called with payload: {}", dto);
        return ResponseEntity.ok(userService.save(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Updates an existing user",
            responses = @ApiResponse(responseCode = "200", description = "User updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))))
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @RequestBody UserDTO dto) {
        logger.info("PUT /users/{} - updateUser called with payload: {}", id, dto);
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user by their ID",
            responses = @ApiResponse(responseCode = "204", description = "User deleted"))
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        logger.info("DELETE /users/{} - deleteUser called", id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/top/week")
    @Operation(summary = "Top users of the week", description = "Returns top-performing users for the current week",
            responses = @ApiResponse(responseCode = "200", description = "Top users",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserTopDTO.class))))
    public List<UserTopDTO> getTopUsersWeek() {
        LocalDateTime now = LocalDateTime.now();
        return userService.getTopUsersBetween(now.minusWeeks(1), now);
    }

    @GetMapping("/top/month")
    @Operation(summary = "Top users of the month", description = "Returns top-performing users for the current month",
            responses = @ApiResponse(responseCode = "200", description = "Top users",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserTopDTO.class))))
    public List<UserTopDTO> getTopUsersMonth() {
        LocalDateTime now = LocalDateTime.now();
        return userService.getTopUsersBetween(now.minusMonths(1), now);
    }

    @GetMapping("/top/all")
    @Operation(summary = "Top users of all time", description = "Returns top-performing users of all time",
            responses = @ApiResponse(responseCode = "200", description = "Top users",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserTopDTO.class))))
    public List<UserTopDTO> getTopUsersAllTime() {
        return userService.getTopUsersBetween(LocalDateTime.MIN, LocalDateTime.now());
    }

    @GetMapping("/profile")
    @Operation(summary = "User profile page", description = "Returns placeholder message for profile access",
            responses = @ApiResponse(responseCode = "200", description = "Profile access granted",
                    content = @Content(mediaType = "text/plain")))
    public String profile() {
        logger.info("GET /users/profile - profile accessed");
        return "Доступ к профилю пользователя";
    }

    @GetMapping("/eco-hero")
    @Operation(summary = "Get eco-hero of the week", description = "Returns the most eco-active user of the week",
            responses = @ApiResponse(responseCode = "200", description = "Eco-hero found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EcoHeroDTO.class))))
    public EcoHeroDTO getEcoHeroOfTheWeek() {
        return analyticsService.getEcoHeroOfTheWeek();
    }

    @PostMapping("/join-team")
    @Operation(summary = "Join a team", description = "Allows user to join a team",
            responses = @ApiResponse(responseCode = "200", description = "User joined team",
                    content = @Content(mediaType = "text/plain")))
    public String joinTeam() {
        logger.info("POST /users/join-team - joinTeam called");
        return "Пользователь вступил в команду";
    }

    @PostMapping("/join-event")
    @Operation(summary = "Join an event", description = "Allows user to join an event",
            responses = @ApiResponse(responseCode = "200", description = "User joined event",
                    content = @Content(mediaType = "text/plain")))
    public String joinEvent() {
        logger.info("POST /users/join-event - joinEvent called");
        return "Пользователь вступил в мероприятие";
    }
}
