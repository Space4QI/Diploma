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

    public AchievementDTO save(AchievementDTO dto) {
        logger.info("Saving achievement: {}", dto);
        Achievement saved = achievementRepository.save(achievementMapper.toEntity(dto));
        logger.info("Achievement saved with ID: {}", saved.getId());
        return achievementMapper.toDTO(saved);
    }

    public void checkAndAssign(User user) {
        long participationCount = userEventRepository.countByUser_Id(user.getId());
        logger.info("User {} has {} participations", user.getNickname(), participationCount);

        if (participationCount >= 1) {
            assignIfNotExists(user, "Первый шаг");
        }
        // Ачивка за вступление в команду
        if (user.getTeam() != null) {
            assignIfNotExists(user, "Присоединился к команде");
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
                ref.setAchievement(achievement);
                crossRefRepository.save(ref);
                logger.info("Achievement '{}' assigned to user {}", achievementTitle, user.getNickname());
            } else {
                logger.debug("User {} already has achievement '{}'", user.getNickname(), achievementTitle);
            }
        } else {
            logger.warn("Achievement '{}' not found in database", achievementTitle);
        }
    }
}
