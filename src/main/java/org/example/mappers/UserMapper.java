package org.example.mappers;

import org.example.Dto.AchievementDTO;
import org.example.Dto.UserDTO;
import org.example.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO toDTO(User user) {
        UserDTO dto = modelMapper.map(user, UserDTO.class);

        if (user.getAchievementRefs() != null) {
            dto.setAchievements(
                    user.getAchievementRefs().stream()
                            .map(ref -> new AchievementDTO(
                                    ref.getAchievement().getId(),
                                    ref.getAchievement().getTitle(),
                                    ref.getAchievement().getDescription(),
                                    ref.getAchievement().getImageResId()
                            ))
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public User toEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }
}
