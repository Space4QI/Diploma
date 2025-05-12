package org.example.services;

import jakarta.transaction.Transactional;
import org.example.Dto.EventDTO;
import org.example.mappers.EventMapper;
import org.example.models.Event;
import org.example.repositories.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    public EventService(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Cacheable("unverifiedEvents")
    public List<EventDTO> getAll() {
        log.info(">>> Загружаем все события из БД...");
        List<EventDTO> result = eventRepository.findAll()
                .stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
        log.info(">>> Загружено {} событий", result.size());
        return result;
    }

    public Optional<EventDTO> getById(UUID id) {
        return eventRepository.findById(id)
                .map(eventMapper::toDTO);
    }

    public EventDTO save(EventDTO dto) {
        Event saved = eventRepository.save(eventMapper.toEntity(dto));
        return eventMapper.toDTO(saved);
    }

    public void delete(UUID id) {
        eventRepository.deleteById(id);
    }

    public void finishEvent(UUID id) {
        System.out.println("Finishing event: " + id);
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        event.setFinished(true);
        eventRepository.save(event);
    }

}