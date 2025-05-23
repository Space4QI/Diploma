package org.example.services;

import org.example.Dto.TeamDTO;
import org.example.Dto.TeamTopDTO;
import org.example.mappers.TeamMapper;
import org.example.models.Event;
import org.example.models.Team;
import org.example.models.User;
import org.example.repositories.EventRepository;
import org.example.repositories.TeamRepository;
import org.example.repositories.UserEventRepository;
import org.example.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private static final Logger logger = LoggerFactory.getLogger(TeamService.class);

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMapper teamMapper;
    private final EventRepository eventRepository;
    private final AchievementService achievementService;
    private final UserEventRepository userEventRepository;

    public TeamService(TeamRepository teamRepository,
                       UserRepository userRepository,
                       TeamMapper teamMapper,
                       EventRepository eventRepository,
                       AchievementService achievementService, UserEventRepository userEventRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.teamMapper = teamMapper;
        this.eventRepository = eventRepository;
        this.achievementService = achievementService;
        this.userEventRepository = userEventRepository;
    }

    @Cacheable("teams")
    public List<TeamDTO> getAll() {
        logger.info("Fetching all teams from DB (cache: teams)");
        return teamRepository.findAll()
                .stream()
                .map(teamMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "team_by_id", key = "#id")
    public Optional<TeamDTO> getById(UUID id) {
        logger.info("Fetching team by ID: {} (cache: team_by_id)", id);
        return teamRepository.findById(id)
                .map(teamMapper::toDTO);
    }

    public TeamDTO save(TeamDTO dto) {
        logger.info("Saving new team: {}", dto);
        Team saved = teamRepository.save(teamMapper.toEntity(dto));
        logger.info("Team saved with ID: {}", saved.getId());
        return teamMapper.toDTO(saved);
    }

    public void delete(UUID id) {
        logger.info("Deleting team with ID: {}", id);
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Team not found for deletion: {}", id);
                    return new RuntimeException("Team not found");
                });

        List<Event> relatedEvents = eventRepository.findByTeams_Id(team.getId());
        for (Event event : relatedEvents) {
            logger.info("Removing team {} from event {}", team.getId(), event.getId());
            event.getTeams().remove(team);
            eventRepository.save(event);
        }

        List<User> usersWithTeam = userRepository.findByTeamId(team.getId());
        for (User user : usersWithTeam) {
            logger.info("Unassigning team {} from user {}", team.getId(), user.getId());
            user.setTeam(null);
            userRepository.save(user);
        }

        teamRepository.delete(team);
        logger.info("Team {} deleted successfully", id);
    }

    public void joinTeam(UUID teamId, UUID userId) {
        logger.info("Joining user {} to team {}", userId, teamId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User not found: {}", userId);
                    return new RuntimeException("User not found");
                });
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> {
                    logger.warn("Team not found: {}", teamId);
                    return new RuntimeException("Team not found");
                });

        user.setTeam(team);
        user.setJoinedTeamAt(LocalDateTime.now());
        userRepository.save(user);
        logger.info("User {} joined team {}", userId, teamId);

        achievementService.checkAndAssign(user);
        logger.info("Achievements reassessed for user {}", userId);
    }

    public void leaveTeam(UUID userId) {
        logger.info("User {} leaving team", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User not found: {}", userId);
                    return new RuntimeException("User not found");
                });
        user.setTeam(null);
        userRepository.save(user);
        logger.info("User {} left the team", userId);
    }

    public List<TeamTopDTO> getTopTeamsBetween(LocalDateTime from, LocalDateTime to) {
        return teamRepository.findAll().stream()
                .map(team -> new TeamTopDTO(team.getName(), team.getPoints()))
                .sorted(Comparator.comparingInt(TeamTopDTO::getTotalPoints).reversed())
                .limit(10)
                .toList();
    }
}
