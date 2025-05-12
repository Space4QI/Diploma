package org.example.services;

import org.example.Dto.TeamDTO;
import org.example.mappers.TeamMapper;
import org.example.models.Event;
import org.example.models.Team;
import org.example.models.User;
import org.example.repositories.TeamRepository;
import org.example.repositories.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.example.repositories.EventRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMapper teamMapper;
    private final EventRepository eventRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository, TeamMapper teamMapper, EventRepository eventRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.teamMapper = teamMapper;
        this.eventRepository = eventRepository;
    }

    @Cacheable("teams")
    public List<TeamDTO> getAll() {
        return teamRepository.findAll()
                .stream()
                .map(teamMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<TeamDTO> getById(UUID id) {
        return teamRepository.findById(id).map(teamMapper::toDTO);
    }

    public TeamDTO save(TeamDTO dto) {
        Team saved = teamRepository.save(teamMapper.toEntity(dto));
        return teamMapper.toDTO(saved);
    }

    public void delete(UUID id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        for (Event event : eventRepository.findByTeams_Id(team.getId())) {
            event.getTeams().remove(team);
            eventRepository.save(event);
        }
        List<User> usersWithTeam = userRepository.findByTeamId(team.getId());
        for (User user : usersWithTeam) {
            user.setTeam(null);
            userRepository.save(user);
        }
        teamRepository.delete(team);
    }


    public void joinTeam(UUID teamId, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Team team = teamRepository.findById(teamId).orElseThrow();
        user.setTeam(team);
        userRepository.save(user);
    }

    public void leaveTeam(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setTeam(null);
        userRepository.save(user);
    }
}