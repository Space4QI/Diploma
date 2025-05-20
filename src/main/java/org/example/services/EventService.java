package org.example.services;

import jakarta.transaction.Transactional;
import org.example.Dto.EventDTO;
import org.example.mappers.EventMapper;
import org.example.models.*;
import org.example.repositories.EventRepository;
import org.example.repositories.UserEventRepository;
import org.example.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;
    private final UserEventRepository userEventRepository;
    private final AchievementService achievementService;

    public EventService(EventRepository eventRepository,
                        EventMapper eventMapper,
                        UserRepository userRepository,
                        UserEventRepository userEventRepository,
                        AchievementService achievementService) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.userRepository = userRepository;
        this.userEventRepository = userEventRepository;
        this.achievementService = achievementService;
    }

    @Cacheable("unverifiedEvents")
    public List<EventDTO> getAll() {
        log.info("Fetching all events from DB (cache: unverifiedEvents)");
        List<EventDTO> result = eventRepository.findAll()
                .stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
        log.info("Loaded {} events", result.size());
        return result;
    }

    @Cacheable(value = "event_by_id", key = "#id")
    public Optional<EventDTO> getById(UUID id) {
        log.info("Fetching event by ID: {} (cache: event_by_id)", id);
        return eventRepository.findById(id)
                .map(eventMapper::toDTO);
    }

    public EventDTO save(EventDTO dto) {
        log.info("Saving new event: {}", dto);
        Event event = eventMapper.toEntity(dto);
        Event savedEvent = eventRepository.save(event);

        User creator = userRepository.findById(dto.getCreatorId())
                .orElseThrow(() -> {
                    log.warn("Creator not found: {}", dto.getCreatorId());
                    return new RuntimeException("Creator not found");
                });

        UserEventCrossRef ref = new UserEventCrossRef(creator, savedEvent);
        userEventRepository.save(ref);

        creator.setEventCount(creator.getEventCount() + 1);
        userRepository.save(creator);

        log.info("Event saved with ID: {}", savedEvent.getId());
        return eventMapper.toDTO(savedEvent);
    }

    public void joinEvent(UUID eventId, UUID userId) {
        log.info("User {} joining event {}", userId, eventId);
        User user = userRepository.findById(userId).orElseThrow();
        Event event = eventRepository.findById(eventId).orElseThrow();

        boolean alreadyJoined = userEventRepository.existsById(new UserEventKey(userId, eventId));
        if (!alreadyJoined) {
            UserEventCrossRef ref = new UserEventCrossRef(user, event);
            userEventRepository.save(ref);

            user.setEventCount(user.getEventCount() + 1);
            userRepository.save(user);
            log.info("User {} successfully joined event {}", userId, eventId);
        } else {
            log.info("User {} already joined event {}", userId, eventId);
        }
    }

    @Transactional
    public void leaveEvent(UUID eventId, UUID userId) {
        log.info("User {} leaving event {}", userId, eventId);
        User user = userRepository.findById(userId).orElseThrow();
        Event event = eventRepository.findById(eventId).orElseThrow();

        userEventRepository.deleteByUserAndEvent(user, event);

        if (user.getEventCount() > 0) {
            user.setEventCount(user.getEventCount() - 1);
            userRepository.save(user);
            log.info("User {} left event {}. Event count decreased.", userId, eventId);
        } else {
            log.warn("User {} has event count zero, nothing to decrement", userId);
        }
    }

    public void participateInEvent(UUID eventId, UUID userId) {
        log.info("User {} participating in event {}", userId, eventId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User not found: {}", userId);
                    return new RuntimeException("User not found");
                });
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> {
                    log.warn("Event not found: {}", eventId);
                    return new RuntimeException("Event not found");
                });

        if (!userEventRepository.existsByUserAndEvent(user, event)) {
            UserEventCrossRef ref = new UserEventCrossRef();
            ref.setUser(user);
            ref.setEvent(event);
            userEventRepository.save(ref);
            log.info("User {} marked as participant in event {}", userId, eventId);
        }

        achievementService.checkAndAssign(user);
        log.info("Achievements reassessed for user {}", userId);
    }

    @Cacheable(value = "events_by_team", key = "#teamId")
    public List<EventDTO> getEventsByTeam(UUID teamId) {
        log.info("Fetching events for team: {} (cache: events_by_team)", teamId);
        List<EventDTO> events = eventRepository.findByTeams_Id(teamId)
                .stream()
                .map(eventMapper::toDTO)
                .toList();
        log.info("Found {} events for team {}", events.size(), teamId);
        return events;
    }

    public void delete(UUID id) {
        log.info("Deleting event with ID: {}", id);
        eventRepository.deleteById(id);
        log.info("Event {} deleted", id);
    }

    public void finishEvent(UUID id) {
        log.info("Marking event {} as finished", id);

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Event not found: {}", id);
                    return new RuntimeException("Event not found");
                });

        if (!event.isCompleted()) {
            event.setCompleted(true);
            eventRepository.save(event);
            log.info("Event {} marked as completed", id);

            List<UserEventCrossRef> participants = userEventRepository.findByEventId(event.getId());
            for (UserEventCrossRef ref : participants) {
                User user = ref.getUser();
                user.setPoints(user.getPoints() + 10);
                userRepository.save(user);
                log.info("Assigned points to user {} for completing event {}", user.getId(), event.getId());

                achievementService.checkAndAssign(user);
            }
        } else {
            log.info("Event {} already marked as completed", id);
        }
    }

}
