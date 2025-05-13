package org.example.mappers;

import org.example.Dto.EventDTO;
import org.example.models.Event;
import org.example.models.Team;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class EventMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public EventMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EventDTO toDTO(Event event) {
        EventDTO dto = modelMapper.map(event, EventDTO.class);

        dto.setTeamId(
                event.getTeams().stream().findFirst().map(Team::getId).orElse(null)
        );

        dto.setTeamName(
                event.getTeams().stream().findFirst().map(Team::getName).orElse(null)
        );

        return dto;
    }

    public Event toEntity(EventDTO dto) {
        Event event = modelMapper.map(dto, Event.class);

        if (dto.getTeamId() != null) {
            Team team = new Team();
            team.setId(dto.getTeamId());
            event.setTeams(Set.of(team));
        }

        return event;
    }
}