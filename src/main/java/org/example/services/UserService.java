package org.example.services;

import org.example.Dto.UserDTO;
import org.example.Dto.UserTopDTO;
import org.example.mappers.UserMapper;
import org.example.models.Role;
import org.example.models.User;
import org.example.models.UserEventCrossRef;
import org.example.repositories.UserEventRepository;
import org.example.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserEventRepository userEventRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, UserEventRepository userEventRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userEventRepository = userEventRepository;
        this.passwordEncoder = passwordEncoder;
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

        User user = userMapper.toEntity(dto);

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        user = userRepository.save(user);
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

    public List<UserTopDTO> getTopUsersBetween(LocalDateTime from, LocalDateTime to) {
        List<UserEventCrossRef> refs = userEventRepository.findAllWithUserAndEvent();
        Map<UUID, Integer> pointsMap = new HashMap<>();
        from = from.withHour(0).withMinute(0).withSecond(0).withNano(0);
        to = to.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);
        System.out.println("FROM: " + from);
        System.out.println("TO: " + to);

        System.out.println("TOTAL refs: " + refs.size());
        for (UserEventCrossRef ref : refs) {
            try {
                String rawDateTime = ref.getEvent().getDateTime();
                LocalDateTime eventTime = LocalDateTime.parse(rawDateTime);
                System.out.println("------------");
                System.out.println("User: " + ref.getUser().getNickname());
                System.out.println("User points: " + ref.getUser().getPoints());
                System.out.println("Event: " + ref.getEvent().getTitle());
                System.out.println("Event datetime: " + rawDateTime);
                System.out.println("Parsed eventTime: " + eventTime);

                boolean inRange = !eventTime.isBefore(from) && !eventTime.isAfter(to);
                System.out.println("inRange: " + inRange);

                if (inRange) {
                    UUID userId = ref.getUser().getId();
                    int userPoints = ref.getUser().getPoints();
                    pointsMap.put(userId, Math.max(pointsMap.getOrDefault(userId, 0), userPoints));
                }

            } catch (Exception e) {
                System.out.println("FAILED TO PARSE: " + ref.getEvent().getDateTime());
                e.printStackTrace();
            }
        }

        List<UserTopDTO> result = refs.stream()
                .map(UserEventCrossRef::getUser)
                .distinct()
                .filter(u -> pointsMap.containsKey(u.getId()))
                .map(u -> new UserTopDTO(u.getNickname(), pointsMap.get(u.getId())))
                .sorted(Comparator.comparingInt(UserTopDTO::getPoints).reversed())
                .limit(10)
                .collect(Collectors.toList());

        System.out.println("FINAL RESULT SIZE: " + result.size());
        return result;
    }

    public UserTopDTO getEcoHeroOfTheWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekAgo = now.minusWeeks(1);

        List<UserEventCrossRef> refs = userEventRepository.findAll();

        Map<UUID, Integer> pointsMap = new HashMap<>();
        Map<UUID, Integer> eventCountMap = new HashMap<>();
        Map<UUID, String> nicknameMap = new HashMap<>();

        for (UserEventCrossRef ref : refs) {
            try {
                LocalDateTime eventTime = LocalDateTime.parse(ref.getEvent().getDateTime());
                if (eventTime.isAfter(weekAgo) && eventTime.isBefore(now)) {
                    UUID userId = ref.getUser().getId();
                    User user = ref.getUser();

                    pointsMap.put(userId, pointsMap.getOrDefault(userId, 0) + user.getPoints());
                    eventCountMap.put(userId, eventCountMap.getOrDefault(userId, 0) + 1);
                    nicknameMap.putIfAbsent(userId, user.getNickname());
                }
            } catch (Exception ignored) {}
        }

        return pointsMap.entrySet().stream()
                .sorted((a, b) -> {
                    int cmp = Integer.compare(b.getValue(), a.getValue()); // сначала по очкам
                    if (cmp == 0) {
                        return Integer.compare(eventCountMap.getOrDefault(b.getKey(), 0),
                                eventCountMap.getOrDefault(a.getKey(), 0)); // потом по числу событий
                    }
                    return cmp;
                })
                .findFirst()
                .map(entry -> new UserTopDTO(nicknameMap.get(entry.getKey()), entry.getValue()))
                .orElse(null);
    }
}
