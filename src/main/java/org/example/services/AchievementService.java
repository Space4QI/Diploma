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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final EventRepository eventRepository;
    private final UserAchievementRepository crossRefRepository;
    private final UserEventRepository userEventRepository;

    private final AchievementMapper achievementMapper;

    public AchievementService(AchievementRepository achievementRepository, AchievementMapper achievementMapper, EventRepository eventRepository, UserAchievementRepository crossRefRepository, UserEventRepository userEventRepository) {
        this.achievementRepository = achievementRepository;
        this.achievementMapper = achievementMapper;
        this.eventRepository = eventRepository;
        this.crossRefRepository = crossRefRepository;
        this.userEventRepository = userEventRepository;
    }

    @Cacheable("achievements")
    public List<AchievementDTO> getAll() {
        return achievementRepository.findAll()
                .stream()
                .map(achievementMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AchievementDTO save(AchievementDTO dto) {
        Achievement saved = achievementRepository.save(achievementMapper.toEntity(dto));
        return achievementMapper.toDTO(saved);
    }

    public void checkAndAssign(User user) {
        long participationCount = userEventRepository.countByUser_Id(user.getId());
        System.out.println(">>> user " + user.getNickname() + " has " + participationCount + " participations");

        if (participationCount >= 1) {
            assignIfNotExists(user, "Первый шаг");
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
            }
        }
    }
}