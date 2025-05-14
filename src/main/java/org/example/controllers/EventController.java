package org.example.controllers;

import jakarta.transaction.Transactional;
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
        log.info("[GET /events] getAllEvents called");
        List<EventDTO> events = eventService.getAll();
        log.info("Found {} events", events.size());
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable UUID id) {
        log.info("[GET /events/{}] getEventById called", id);
        return eventService.getById(id)
                .map(event -> {
                    log.info("Event found: {}", event);
                    return ResponseEntity.ok(event);
                })
                .orElseGet(() -> {
                    log.warn("Event with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO dto) {
        log.info("[POST /events] createEvent called with payload: {}", dto);
        EventDTO saved = eventService.save(dto);
        log.info("Event created: {}", saved);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
        log.info("[DELETE /events/{}] deleteEvent called", id);
        eventService.delete(id);
        log.info("Event {} deleted", id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/finish")
    public ResponseEntity<Void> finishEvent(@PathVariable UUID id) {
        log.info("[PUT /events/{}/finish] finishEvent called", id);
        eventService.finishEvent(id);
        log.info("Event {} marked as finished", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{eventId}/join/{userId}")
    public ResponseEntity<Void> joinEvent(@PathVariable UUID eventId, @PathVariable UUID userId) {
        log.info("[POST /events/{}/join/{}] joinEvent called", eventId, userId);
        eventService.joinEvent(eventId, userId);
        log.info("User {} joined event {}", userId, eventId);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("/{eventId}/leave/{userId}")
    public ResponseEntity<Void> leaveEvent(@PathVariable UUID eventId, @PathVariable UUID userId) {
        log.info("[DELETE /events/{}/leave/{}] leaveEvent called", eventId, userId);
        eventService.leaveEvent(eventId, userId);
        log.info("User {} left event {}", userId, eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<EventDTO>> getEventsByTeam(@PathVariable UUID teamId) {
        log.info("[GET /events/team/{}] getEventsByTeam called", teamId);
        List<EventDTO> events = eventService.getEventsByTeam(teamId);
        log.info("Found {} events for team {}", events.size(), teamId);
        return ResponseEntity.ok(events);
    }

    @PostMapping("/{eventId}/participate/{userId}")
    public ResponseEntity<Void> participateInEvent(@PathVariable UUID eventId,
                                                   @PathVariable UUID userId) {
        log.info("[POST /events/{}/participate/{}] participateInEvent called", eventId, userId);
        eventService.participateInEvent(eventId, userId);
        log.info("User {} participated in event {}", userId, eventId);
        return ResponseEntity.noContent().build();
    }
}
