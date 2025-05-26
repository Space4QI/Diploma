package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Events", description = "Operations related to event creation, management, and participation")
public class EventController {

    private final EventService eventService;
    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    @Operation(
            summary = "Get all events",
            description = "Returns a list of all created events",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of events",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventDTO.class)))
            }
    )
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        log.info("[GET /events] getAllEvents called");
        List<EventDTO> events = eventService.getAll();
        log.info("Found {} events", events.size());
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get event by ID",
            description = "Returns an event by its UUID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Event found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Event not found")
            }
    )
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
    @Operation(
            summary = "Create a new event",
            description = "Creates a new event and returns the saved object",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Event created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventDTO.class)))
            }
    )
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO dto) {
        log.info("[POST /events] createEvent called with payload: {}", dto);
        EventDTO saved = eventService.save(dto);
        log.info("Event created: {}", saved);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an event",
            description = "Deletes an event by its ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Event deleted"),
                    @ApiResponse(responseCode = "404", description = "Event not found")
            }
    )
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
        log.info("[DELETE /events/{}] deleteEvent called", id);
        eventService.delete(id);
        log.info("Event {} deleted", id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/finish")
    @Operation(
            summary = "Finish an event",
            description = "Marks the event as completed",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Event marked as finished")
            }
    )
    public ResponseEntity<Void> finishEvent(@PathVariable UUID id) {
        log.info("[PUT /events/{}/finish] finishEvent called", id);
        eventService.finishEvent(id);
        log.info("Event {} marked as finished", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{eventId}/join/{userId}")
    @Operation(
            summary = "Join event",
            description = "User joins the event",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User joined the event")
            }
    )
    public ResponseEntity<Void> joinEvent(@PathVariable UUID eventId, @PathVariable UUID userId) {
        log.info("[POST /events/{}/join/{}] joinEvent called", eventId, userId);
        eventService.joinEvent(eventId, userId);
        log.info("User {} joined event {}", userId, eventId);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("/{eventId}/leave/{userId}")
    @Operation(
            summary = "Leave event",
            description = "User leaves the event",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User left the event")
            }
    )
    public ResponseEntity<Void> leaveEvent(@PathVariable UUID eventId, @PathVariable UUID userId) {
        log.info("[DELETE /events/{}/leave/{}] leaveEvent called", eventId, userId);
        eventService.leaveEvent(eventId, userId);
        log.info("User {} left event {}", userId, eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/team/{teamId}")
    @Operation(
            summary = "Get events by team",
            description = "Returns events associated with a specific team",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of team events",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventDTO.class)))
            }
    )
    public ResponseEntity<List<EventDTO>> getEventsByTeam(@PathVariable UUID teamId) {
        log.info("[GET /events/team/{}] getEventsByTeam called", teamId);
        List<EventDTO> events = eventService.getEventsByTeam(teamId);
        log.info("Found {} events for team {}", events.size(), teamId);
        return ResponseEntity.ok(events);
    }

    @PostMapping("/{eventId}/participate/{userId}")
    @Operation(
            summary = "Participate in event",
            description = "Confirms that a user participated in an event",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User participation recorded")
            }
    )
    public ResponseEntity<Void> participateInEvent(@PathVariable UUID eventId,
                                                   @PathVariable UUID userId) {
        log.info("[POST /events/{}/participate/{}] participateInEvent called", eventId, userId);
        eventService.participateInEvent(eventId, userId);
        log.info("User {} participated in event {}", userId, eventId);
        return ResponseEntity.noContent().build();
    }
}
