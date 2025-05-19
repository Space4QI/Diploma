package org.example.services;

import org.example.Dto.ActivityDTO;
import org.example.models.Event;
import org.example.models.User;
import org.example.models.UserEventCrossRef;
import org.example.repositories.UserEventRepository;
import org.example.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private final UserEventRepository userEventRepository;
    private final UserRepository userRepository;

    public FeedService(UserEventRepository userEventRepository, UserRepository userRepository) {
        this.userEventRepository = userEventRepository;
        this.userRepository = userRepository;
    }

    public List<ActivityDTO> getRecentActivities(UUID userId) {
        List<ActivityDTO> activities = new ArrayList<>();

        // 1. Присоединился к мероприятию
        for (UserEventCrossRef ref : userEventRepository.findAllWithUserAndEvent()) {
            if (!ref.getUser().getId().equals(userId)) {
                try {
                    LocalDateTime eventTime = LocalDateTime.parse(ref.getEvent().getDateTime());
                    activities.add(new ActivityDTO(
                            ref.getUser().getNickname() + " присоединился к мероприятию \"" + ref.getEvent().getTitle() + "\"",
                            eventTime.toString()
                    ));
                } catch (Exception ignored) {}
            }
        }

        // 2. Присоединился к команде
        for (User user : userRepository.findAll()) {
            if (!user.getId().equals(userId) && user.getTeam() != null) {
                activities.add(new ActivityDTO(
                        user.getNickname() + " вступил в команду \"" + user.getTeam().getName() + "\"",
                        LocalDateTime.now().toString()  // если нет даты присоединения
                ));
            }
        }

        return activities.stream()
                .sorted(Comparator.comparing(ActivityDTO::getTimestamp).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }


}
