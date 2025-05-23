package org.example.controllers;

import org.example.Dto.UserDTO;
import org.example.Dto.UserTopDTO;
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
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("GET /users - getAllUsers called");
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
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
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto) {
        logger.info("POST /users - createUser called with payload: {}", dto);
        return ResponseEntity.ok(userService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @RequestBody UserDTO dto) {
        logger.info("PUT /users/{} - updateUser called with payload: {}", id, dto);
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        logger.info("DELETE /users/{} - deleteUser called", id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/top/week")
    public List<UserTopDTO> getTopUsersWeek() {
        LocalDateTime now = LocalDateTime.now();
        return userService.getTopUsersBetween(now.minusWeeks(1), now);
    }

    @GetMapping("/top/month")
    public List<UserTopDTO> getTopUsersMonth() {
        LocalDateTime now = LocalDateTime.now();
        return userService.getTopUsersBetween(now.minusMonths(1), now);
    }

    @GetMapping("/top/all")
    public List<UserTopDTO> getTopUsersAllTime() {
        return userService.getTopUsersBetween(LocalDateTime.MIN, LocalDateTime.now());
    }

    @GetMapping("/profile")
    public String profile() {
        logger.info("GET /users/profile - profile accessed");
        return "Доступ к профилю пользователя";
    }

    @GetMapping("/eco-hero")
    public UserTopDTO getEcoHeroOfTheWeek() {
        return userService.getEcoHeroOfTheWeek();
    }

    @PostMapping("/join-team")
    public String joinTeam() {
        logger.info("POST /users/join-team - joinTeam called");
        return "Пользователь вступил в команду";
    }

    @PostMapping("/join-event")
    public String joinEvent() {
        logger.info("POST /users/join-event - joinEvent called");
        return "Пользователь вступил в мероприятие";
    }
}
