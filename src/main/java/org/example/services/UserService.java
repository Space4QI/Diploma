package org.example.services;

import org.example.Dto.UserDTO;
import org.example.mappers.UserMapper;
import org.example.models.Role;
import org.example.models.User;
import org.example.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Cacheable("users_all")
    public List<UserDTO> getAll() {
        logger.info("Fetching all users from DB (cache: users_all)");
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "user_by_id", key = "#id")
    public Optional<UserDTO> getById(UUID id) {
        logger.info("Fetching user by ID: {} (cache: user_by_id)", id);
        return userRepository.findById(id)
                .map(userMapper::toDTO);
    }

    @Cacheable(value = "user_by_phone", key = "#phone")
    public Optional<UserDTO> getByPhone(String phone) {
        logger.info("Fetching user by phone: {} (cache: user_by_phone)", phone);
        return userRepository.findByPhone(phone)
                .map(userMapper::toDTO);
    }

    public UserDTO save(UserDTO dto) {
        logger.info("Saving new user: {}", dto);
        User user = userRepository.save(userMapper.toEntity(dto));
        logger.info("User saved with ID: {}", user.getId());
        return userMapper.toDTO(user);
    }

    public void assignRole(UUID userId, String role) {
        logger.info("Assigning role '{}' to user {}", role, userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User not found: {}", userId);
                    return new RuntimeException("User not found");
                });
        user.setRole(Role.valueOf(role.toUpperCase()));
        userRepository.save(user);
        logger.info("Role '{}' assigned to user {}", role, userId);
    }

    public List<UserDTO> getUsersByTeam(UUID teamId) {
        logger.info("Fetching users for team: {}", teamId);
        List<UserDTO> users = userRepository.findByTeamId(teamId)
                .stream()
                .map(userMapper::toDTO)
                .toList();
        logger.info("Found {} users in team {}", users.size(), teamId);
        return users;
    }

    public void delete(UUID id) {
        logger.info("Deleting user with ID: {}", id);
        userRepository.deleteById(id);
        logger.info("User {} deleted", id);
    }

    public UserDTO update(UUID id, UserDTO dto) {
        logger.info("Updating user {} with data: {}", id, dto);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User not found for update: {}", id);
                    return new RuntimeException("User not found");
                });

        user.setName(dto.getName());
        user.setNickname(dto.getNickname());
        user.setRole(dto.getRole());
        user.setPoints(dto.getPoints());
        user.setEventCount(dto.getEventCount());
        user.setAvatarUri(dto.getAvatarUri());

        User updated = userRepository.save(user);
        logger.info("User {} updated successfully", id);
        return userMapper.toDTO(updated);
    }
}
