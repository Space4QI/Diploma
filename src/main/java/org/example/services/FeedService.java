package org.example.services;

import org.example.Dto.ActivityDTO;
import org.example.models.Event;
import org.example.models.User;
import org.example.models.UserEventCrossRef;
import org.example.repositories.EventRepository;
import org.example.repositories.UserEventRepository;
import org.example.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private final UserEventRepository userEventRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public FeedService(UserEventRepository userEventRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.userEventRepository = userEventRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public List<ActivityDTO> getRecentActivities() {
        List<ActivityDTO> activities = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Set<UUID> completedEvents = new HashSet<>();

        // 1. Обрабатываем мероприятия
        for (Event event : eventRepository.findAll()) {
            try {
                LocalDateTime startTime = LocalDateTime.parse(event.getDateTime());

                if (event.isCompleted() && event.getCompletedAt() != null) {
                    activities.add(new ActivityDTO(
                            "Мероприятие \"" + event.getTitle() + "\" было завершено",
                            event.getCompletedAt().format(formatter)
                    ));
                } else if (startTime.isBefore(LocalDateTime.now())) {
                    activities.add(new ActivityDTO(
                            "Мероприятие \"" + event.getTitle() + "\" началось",
                            startTime.format(formatter)
                    ));
                }

            } catch (Exception ignored) {}
        }

        // 2. Присоединения к завершённым мероприятиям (если нужно)
        for (UserEventCrossRef ref : userEventRepository.findAllWithUserAndEvent()) {
            try {
                Event event = ref.getEvent();
                if (event.isCompleted() && !completedEvents.contains(event.getId())) {
                    LocalDateTime eventTime = LocalDateTime.parse(event.getDateTime());
                    activities.add(new ActivityDTO(
                            ref.getUser().getNickname() + " присоединился к мероприятию \"" + event.getTitle() + "\"",
                            eventTime.format(formatter)
                    ));
                }
            } catch (Exception ignored) {}
        }

        // 3. Вступления в команды
        for (User user : userRepository.findAll()) {
            if (user.getTeam() != null && user.getJoinedTeamAt() != null) {
                activities.add(new ActivityDTO(
                        user.getNickname() + " вступил в команду \"" + user.getTeam().getName() + "\"",
                        user.getJoinedTeamAt().format(formatter)
                ));
            }
        }

        for (ActivityDTO act : activities) {
            System.out.println(">>> FEED: " + act.getMessage() + " @ " + act.getTimestamp());
        }

        return activities.stream()
                .sorted(Comparator.comparing(ActivityDTO::getTimestamp).reversed())
                //.limit(3)
                .collect(Collectors.toList());
    }
}
