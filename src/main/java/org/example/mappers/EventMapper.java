package org.example.mappers;

import org.example.Dto.EventDTO;
import org.example.models.Event;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public EventMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EventDTO toDTO(Event event) {
        return modelMapper.map(event, EventDTO.class);
    }

    public Event toEntity(EventDTO dto) {
        return modelMapper.map(dto, Event.class);
    }
}