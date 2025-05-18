package org.example.services;

import org.example.Dto.ActivityDTO;
import org.example.models.Event;
import org.example.models.User;
import org.example.models.UserEventCrossRef;
import org.example.repositories.UserEventCrossRefRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private final UserEventCrossRefRepository userEventCrossRefRepository;

    public FeedService(UserEventCrossRefRepository userEventCrossRefRepository) {
        this.userEventCrossRefRepository = userEventCrossRefRepository;
    }

    public List<ActivityDTO> getRecentActivities(UUID currentUserId) {
        List<ActivityDTO> activities = new ArrayList<>();

        for (UserEventCrossRef ref : userEventCrossRefRepository.findAll()) {
            User user = ref.getUser();
            Event event = ref.getEvent();

            try {
                LocalDateTime time = LocalDateTime.parse(event.getDateTime());
                activities.add(new ActivityDTO(
                        user.getNickname() + " присоединился к событию \"" + event.getTitle() + "\"",
                        time.toString()
                ));
            } catch (Exception ignored) {
            }

        }

        for (UserEventCrossRef ref : userEventCrossRefRepository.findAll()) {
            Event event = ref.getEvent();
            if (event.isCompleted() && event.isVerified()) {
                try {
                    LocalDateTime time = LocalDateTime.parse(event.getDateTime());
                    event.getTeams().forEach(team -> {
                        activities.add(new ActivityDTO(
                                "Команда \"" + team.getName() + "\" завершила событие \"" + event.getTitle() + "\"",
                                time.toString()
                        ));
                    });
                } catch (Exception ignored) {
                }
            }
        }

        return activities.stream()
                .sorted(Comparator.comparing(ActivityDTO::getTimestamp).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }
}
