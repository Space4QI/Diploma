package org.example.services;

import org.example.Dto.AchievementDTO;
import org.example.mappers.AchievementMapper;
import org.example.models.Achievement;
import org.example.repositories.AchievementRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AchievementService {
    private final AchievementRepository achievementRepository;

    private final AchievementMapper achievementMapper;

    public AchievementService(AchievementRepository achievementRepository, AchievementMapper achievementMapper) {
        this.achievementRepository = achievementRepository;
        this.achievementMapper = achievementMapper;
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
}