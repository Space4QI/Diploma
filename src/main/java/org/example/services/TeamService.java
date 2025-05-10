package org.example.services;

import org.example.Dto.TeamDTO;
import org.example.mappers.TeamMapper;
import org.example.models.Team;
import org.example.models.User;
import org.example.repositories.TeamRepository;
import org.example.repositories.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMapper teamMapper;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.teamMapper = teamMapper;
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