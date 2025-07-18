package org.example.services;

import org.example.Dto.AchievementDTO;
import org.example.mappers.AchievementMapper;
import org.example.models.Achievement;
import org.example.models.User;
import org.example.models.UserAchievementCrossRef;
import org.example.repositories.AchievementRepository;
import org.example.repositories.EventRepository;
import org.example.repositories.UserAchievementRepository;
import org.example.repositories.UserEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AchievementService {

    private static final Logger logger = LoggerFactory.getLogger(AchievementService.class);

    private final AchievementRepository achievementRepository;
    private final EventRepository eventRepository;
    private final UserAchievementRepository crossRefRepository;
    private final UserEventRepository userEventRepository;
    private final AchievementMapper achievementMapper;

    public AchievementService(AchievementRepository achievementRepository,
                              AchievementMapper achievementMapper,
                              EventRepository eventRepository,
                              UserAchievementRepository crossRefRepository,
                              UserEventRepository userEventRepository) {
        this.achievementRepository = achievementRepository;
        this.achievementMapper = achievementMapper;
        this.eventRepository = eventRepository;
        this.crossRefRepository = crossRefRepository;
        this.userEventRepository = userEventRepository;
    }

    @Cacheable("achievements")
    public List<AchievementDTO> getAll() {
        logger.info("Fetching all achievements (cache: achievements)");
        List<AchievementDTO> achievements = achievementRepository.findAll()
                .stream()
                .map(achievementMapper::toDTO)
                .collect(Collectors.toList());
        logger.info("Found {} achievements", achievements.size());
        return achievements;
    }

    public void assignWelcomeAchievement(User user) {
        logger.info("Assigning welcome achievement to user {}", user.getNickname());
        assignIfNotExists(user, "Добро пожаловать");
    }

    public AchievementDTO save(AchievementDTO dto) {
        logger.info("Saving achievement: {}", dto);
        Achievement saved = achievementRepository.save(achievementMapper.toEntity(dto));
        logger.info("Achievement saved with ID: {}", saved.getId());
        return achievementMapper.toDTO(saved);
    }

    public void checkAndAssign(User user) {
        logger.info("Проверка достижений для пользователя {}", user.getNickname());

        // 0. Добро пожаловать
        assignIfNotExists(user, "Добро пожаловать");

        // 1. Первое участие
        long participationCount = userEventRepository.countByUser_Id(user.getId());
        logger.info("User {} has {} participations", user.getNickname(), participationCount);

        if (participationCount >= 1) {
            assignIfNotExists(user, "Первое участие");
        }

        // 2. Постоянный участник (5+ событий)
        if (participationCount >= 5) {
            assignIfNotExists(user, "Постоянный участник");
        }

        // 3. Вступил в команду
        if (user.getTeam() != null) {
            assignIfNotExists(user, "Присоединился к команде");
        }

        // 4. Твой первый ивент (создан 1)
        if (user.getEventCount() == 1) {
            assignIfNotExists(user, "Твой первый ивент");
        }

        // 5. Организатор по жизни (создано 5+)
        if (user.getEventCount() >= 5) {
            assignIfNotExists(user, "Организатор по жизни");
        }

        // 6. Первая сотня (набрал 100+ очков)
        if (user.getPoints() >= 100) {
            assignIfNotExists(user, "Первая сотня");
        }
    }

    private void assignIfNotExists(User user, String achievementTitle) {
        Optional<Achievement> achievementOpt = achievementRepository.findByTitle(achievementTitle);
        if (achievementOpt.isPresent()) {
            Achievement achievement = achievementOpt.get();
            boolean alreadyHas = crossRefRepository.existsByUserAndAchievement(user, achievement);

            if (!alreadyHas) {
                UserAchievementCrossRef ref = new UserAchievementCrossRef();
                ref.setUser(user);
                ref.setUserId(user.getId());
                ref.setAchievement(achievement);
                ref.setAchievementId(achievement.getId());
                crossRefRepository.save(ref);
                logger.info("Achievement '{}' assigned to user {}", achievementTitle, user.getNickname());
            } else {
                logger.debug("User {} already has achievement '{}'", user.getNickname(), achievementTitle);
            }
        } else {
            logger.warn("Achievement '{}' not found in database", achievementTitle);
            throw new IllegalStateException("Achievement not found: " + achievementTitle);
        }
    }

    public List<AchievementDTO> getByUserId(UUID userId) {
        return crossRefRepository.findAll().stream()
                .filter(ref -> ref.getUserId().equals(userId))
                .map(ref -> achievementRepository.findById(ref.getAchievementId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(achievementMapper::toDTO)
                .toList();
    }

    public List<AchievementDTO> getByUserPhone(String phone) {
        return crossRefRepository.findAll().stream()
                .filter(ref -> ref.getUser() != null && phone.equals(ref.getUser().getPhone()))
                .map(ref -> achievementRepository.findById(ref.getAchievementId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(achievementMapper::toDTO)
                .toList();
    }

}
