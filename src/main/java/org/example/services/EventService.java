package org.example.services;

import jakarta.transaction.Transactional;
import org.example.Dto.EventDTO;
import org.example.mappers.EventMapper;
import org.example.models.Event;
import org.example.models.Team;
import org.example.models.User;
import org.example.models.UserEventCrossRef;
import org.example.repositories.EventRepository;
import org.example.repositories.TeamRepository;
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
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private static final Logger log = LoggerFactory.getLogger(EventService.class);
    private final UserRepository userRepository;
    private final UserEventRepository userEventRepository;
    private final AchievementService achievementService;
    private final TeamRepository teamRepository;

    public EventService(EventRepository eventRepository, EventMapper eventMapper, UserRepository userRepository, UserEventRepository userEventRepository, AchievementService achievementService, TeamRepository teamRepository) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.userRepository = userRepository;
        this.userEventRepository = userEventRepository;
        this.achievementService = achievementService;
        this.teamRepository = teamRepository;
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
        Event event = eventMapper.toEntity(dto);

        // 👉 связываем с командой, если она указана
        if (dto.getTeamId() != null) {
            Team team = teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new RuntimeException("Team not found"));
            event.setTeams(Set.of(team));
        }

        Event saved = eventRepository.save(event);
        return eventMapper.toDTO(saved);
    }

    public void participateInEvent(UUID eventId, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));

        if (!userEventRepository.existsByUserAndEvent(user, event)) {
            UserEventCrossRef ref = new UserEventCrossRef();
            ref.setUser(user);
            ref.setEvent(event);
            userEventRepository.save(ref);
        }

        achievementService.checkAndAssign(user);
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