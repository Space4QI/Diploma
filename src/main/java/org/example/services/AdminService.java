package org.example.services;

import org.example.Dto.EventDTO;
import org.example.mappers.EventMapper;
import org.example.models.Event;
import org.example.models.Team;
import org.example.models.User;
import org.example.repositories.EventRepository;
import org.example.repositories.TeamRepository;
import org.example.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    private final EventRepository eventRepository;
    private final TeamRepository teamRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;

    public AdminService(EventRepository eventRepository,
                        TeamRepository teamRepository,
                        EventMapper eventMapper, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.teamRepository = teamRepository;
        this.eventMapper = eventMapper;
        this.userRepository = userRepository;
    }

    @Cacheable("admin_all_teams")
    public List<Team> getAllTeams() {
        logger.info("Fetching all teams (cache: admin_all_teams)");
        List<Team> teams = teamRepository.findAll();
        logger.info("Loaded {} teams", teams.size());
        return teams;
    }

    public List<EventDTO> getEventsToVerify() {
        logger.info("Fetching events to verify");
        List<EventDTO> events = eventRepository.findByCompletedTrueAndVerifiedFalse()
                .stream()
                .map(eventMapper::toDTO)
                .toList();
        logger.info("Found {} events pending verification", events.size());
        return events;
    }

    @Cacheable("admin_sorted_teams")
    public List<Team> getSortedTeams() {
        logger.info("Sorting teams by points (cache: admin_sorted_teams)");
        List<Team> teams = teamRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Team::getPoints).reversed())
                .toList();
        logger.info("Teams sorted, total: {}", teams.size());
        return teams;
    }

    public void addPointsToTeam(UUID teamId, int points) {
        logger.info("Adding {} points to team {}", points, teamId);
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> {
                    logger.warn("Team not found: {}", teamId);
                    return new RuntimeException("Team not found");
                });
        team.setPoints(team.getPoints() + points);
        teamRepository.save(team);
        logger.info("Team {} now has {} points", teamId, team.getPoints());
    }

    public void verifyEvent(UUID eventId, int teamPoints, String comment) {
        logger.info("Verifying event {} with teamPoints={} and comment={}", eventId, teamPoints, comment);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> {
                    logger.warn("Event not found: {}", eventId);
                    return new RuntimeException("Event not found");
                });

        event.setVerified(true);
        event.setRejected(false);
        event.setConfirmationComment(comment);
        eventRepository.save(event);
        logger.info("Event {} verified", eventId);
    }

    public void addPointsToParticipant(UUID eventId, String participantName, int points) {
        logger.info("Adding {} points to participant {} in event {}", points, participantName, eventId);
    }

    public void addPointsToUser(UUID userId, int points) {
        logger.info("Adding {} points to user {}", points, userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User not found: {}", userId);
                    return new RuntimeException("User not found");
                });

        user.setPoints(user.getPoints() + points);
        userRepository.save(user);

        logger.info("User {} now has {} points", userId, user.getPoints());
    }


    public void rejectEvent(UUID eventId, String comment) {
        logger.info("Rejecting event {} with comment: {}", eventId, comment);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> {
                    logger.warn("Event not found: {}", eventId);
                    return new RuntimeException("Event not found");
                });
        event.setVerified(false);
        event.setRejected(true);
        event.setConfirmationComment(comment);
        eventRepository.save(event);
        logger.info("Event {} rejected", eventId);
    }
}
