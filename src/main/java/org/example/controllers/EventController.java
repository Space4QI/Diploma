package org.example.controllers;

import org.example.Dto.EventDTO;
import org.example.services.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        log.info(">>> [GET /events] Запрос получен");
        List<EventDTO> events = eventService.getAll();
        log.info(">>> Найдено событий: {}", events.size());
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable UUID id) {
        return eventService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO dto) {
        EventDTO saved = eventService.save(dto);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/finish")
    public ResponseEntity<Void> finishEvent(@PathVariable UUID id) {
        eventService.finishEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{eventId}/participate/{userId}")
    public ResponseEntity<Void> participateInEvent(@PathVariable UUID eventId,
                                                   @PathVariable UUID userId) {
        eventService.participateInEvent(eventId, userId);
        return ResponseEntity.noContent().build();
    }
}