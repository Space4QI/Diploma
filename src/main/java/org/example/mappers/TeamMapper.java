package org.example.mappers;

import org.example.Dto.TeamDTO;
import org.example.models.Team;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public TeamMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TeamDTO toDTO(Team team) {
        return modelMapper.map(team, TeamDTO.class);
    }

    public Team toEntity(TeamDTO dto) {
        return modelMapper.map(dto, Team.class);
    }
}