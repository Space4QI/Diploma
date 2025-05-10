package org.example.mappers;

import org.example.Dto.AchievementDTO;
import org.example.models.Achievement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AchievementMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public AchievementMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AchievementDTO toDTO(Achievement achievement) {
        return modelMapper.map(achievement, AchievementDTO.class);
    }

    public Achievement toEntity(AchievementDTO dto) {
        return modelMapper.map(dto, Achievement.class);
    }
}