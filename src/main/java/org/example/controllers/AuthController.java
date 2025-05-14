package org.example.controllers;

import jakarta.validation.Valid;
import org.example.Dto.UserLoginDTO;
import org.example.Dto.UserDTO;
import org.example.models.Role;
import org.example.models.User;
import org.example.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO) {
        logger.info("[POST /auth/register] Register attempt for phone: {}", userDTO.getPhone());

        if (userRepository.findByPhone(userDTO.getPhone()).isPresent()) {
            logger.warn("User with phone {} already exists", userDTO.getPhone());
            return ResponseEntity.badRequest().body("User already exists");
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setNickname(userDTO.getNickname());
        user.setPhone(userDTO.getPhone());
        user.setPassword(userDTO.getPassword());
        user.setRole(Role.USER);

        userRepository.save(user);

        logger.info("User registered successfully: {}", userDTO.getPhone());
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO request) {
        logger.info("[POST /auth/login] Login attempt for phone: {}", request.getPhone());

        return userRepository.findByPhone(request.getPhone())
                .filter(user -> user.getPassword().equals(request.getPassword()))
                .map(user -> {
                    logger.info("Login successful for phone: {}", request.getPhone());
                    return ResponseEntity.ok("Login successful");
                })
                .orElseGet(() -> {
                    logger.warn("Login failed for phone: {}", request.getPhone());
                    return ResponseEntity.status(401).body("Invalid credentials");
                });
    }
}
