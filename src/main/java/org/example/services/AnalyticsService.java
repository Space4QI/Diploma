package org.example.services;

import org.example.Dto.*;
import org.example.models.User;
import org.example.models.UserEventCrossRef;
import org.example.repositories.UserEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final UserEventRepository userEventRepository;

    public AnalyticsService(UserEventRepository userEventRepository) {
        this.userEventRepository = userEventRepository;
    }

    public List<UserStatsDTO> getTopUsers() {
        return userEventRepository.findTopUsers();
    }

    public List<EventStatsDTO> getPopularEvents() {
        return userEventRepository.findPopularEvents();
    }

    public Map<LocalDate, Long> getEventCountByDate() {
        return userEventRepository.countEventsByDate().stream()
                .collect(Collectors.toMap(EventCountByDateDTO::getDate, EventCountByDateDTO::getCount));
    }

    public List<UserStatsDTO> getTopUsersBetween(LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(LocalTime.MAX);
        return userEventRepository.findTopUsersBetween(start, end);
    }

    public List<EventStatsDTO> getPopularEventsBetween(LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(LocalTime.MAX);
        return userEventRepository.findPopularEventsBetween(start, end);
    }

    public List<EventCountByDateDTO> getEventCountByDateBetween(LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(LocalTime.MAX);
        return userEventRepository.countEventsByDateBetween(start, end);
    }

    public List<TeamStatsDTO> getTopTeams() {
        return userEventRepository.getTopTeams();
    }

    public List<ParticipationLogDTO> getParticipationLog() {
        return userEventRepository.getParticipationLog();
    }

    public EcoHeroDTO getEcoHeroOfTheWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekAgo = now.minusWeeks(1);

        List<UserEventCrossRef> refs = userEventRepository.findAllWithUserAndEvent();

        Map<UUID, Integer> pointsMap = new HashMap<>();
        Map<UUID, Integer> eventCountMap = new HashMap<>();
        Map<UUID, String> nicknameMap = new HashMap<>();

        for (UserEventCrossRef ref : refs) {
            try {
                LocalDateTime eventTime = LocalDateTime.parse(ref.getEvent().getDateTime());
                if (!eventTime.isBefore(weekAgo) && !eventTime.isAfter(now)) {
                    UUID userId = ref.getUser().getId();
                    User user = ref.getUser();

                    pointsMap.put(userId, pointsMap.getOrDefault(userId, 0) + user.getPoints());
                    eventCountMap.put(userId, eventCountMap.getOrDefault(userId, 0) + 1);
                    nicknameMap.putIfAbsent(userId, user.getNickname());
                }
            } catch (Exception ignored) {}
        }

        return pointsMap.entrySet().stream()
                .sorted((a, b) -> {
                    int cmp = Integer.compare(b.getValue(), a.getValue());
                    if (cmp == 0) {
                        return Integer.compare(eventCountMap.getOrDefault(b.getKey(), 0),
                                eventCountMap.getOrDefault(a.getKey(), 0));
                    }
                    return cmp;
                })
                .findFirst()
                .map(entry -> new EcoHeroDTO(
                        nicknameMap.get(entry.getKey()),
                        entry.getValue(),
                        eventCountMap.getOrDefault(entry.getKey(), 0)))
                .orElse(null);
    }

}