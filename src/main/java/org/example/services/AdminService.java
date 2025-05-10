package org.example.services;

import org.example.Dto.EventDTO;
import org.example.mappers.EventMapper;
import org.example.models.Event;
import org.example.repositories.EventRepository;
import org.example.repositories.TeamRepository;
import org.springframework.stereotype.Service;
import org.example.models.Team;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    private final EventRepository eventRepository;
    private final TeamRepository teamRepository;
    private final EventMapper eventMapper;

    public AdminService(EventRepository eventRepository,
                        TeamRepository teamRepository,
                        EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.teamRepository = teamRepository;
        this.eventMapper = eventMapper;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public List<EventDTO> getEventsToVerify() {
        return eventRepository.findByCompletedTrueAndVerifiedFalse()
                .stream()
                .map(eventMapper::toDTO)
                .toList();
    }

    public List<Team> getSortedTeams() {
        return teamRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Team::getPoints).reversed())
                .toList();
    }

    public void addPointsToTeam(UUID teamId, int points) {
        Team team = teamRepository.findById(teamId).orElseThrow();
        team.setPoints(team.getPoints() + points);
        teamRepository.save(team);
    }

    public void verifyEvent(UUID eventId, int teamPoints, String comment) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        Team team = event.getTeam();

        event.setVerified(true);
        event.setRejected(false);
        event.setConfirmationComment(comment);
        eventRepository.save(event);

        team.setPoints(team.getPoints() + teamPoints);
        teamRepository.save(team);
    }

    public void addPointsToParticipant(UUID eventId, String participantName, int points) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        System.out.println("Начислено " + points + " очков участнику " + participantName + " в событии " + eventId);
    }

    public void rejectEvent(UUID eventId, String comment) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.setVerified(false);
        event.setRejected(true);
        event.setConfirmationComment(comment);
        eventRepository.save(event);
    }
}
