package org.example.init;

import jakarta.annotation.PostConstruct;
import org.example.models.*;
import org.example.repositories.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final EventRepository eventRepository;
    private final AchievementRepository achievementRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, TeamRepository teamRepository,
                           EventRepository eventRepository, AchievementRepository achievementRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.eventRepository = eventRepository;
        this.achievementRepository = achievementRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        // Команда
        Team team = new Team();
        team.setId(UUID.randomUUID());
        team.setName("Чистюли");
        team.setColor(123456);
        team.setAreaPoints("[]");
        team.setPoints(0);
        teamRepository.save(team);

        // Админ
        User admin = new User();
        admin.setId(UUID.randomUUID());
        admin.setName("Админ");
        admin.setNickname("admin01");
        admin.setPhone("9990000001");
        admin.setPassword(passwordEncoder.encode("adminpass"));
        admin.setRole(Role.ADMIN);
        admin.setPoints(100);
        admin.setEventCount(0);
        admin.setAvatarUri("img/admin.png");
        userRepository.save(admin);

        // Организатор
        User organizer = new User();
        organizer.setId(UUID.randomUUID());
        organizer.setName("Организатор");
        organizer.setNickname("org01");
        organizer.setPhone("9990000002");
        organizer.setPassword(passwordEncoder.encode("orgpass"));
        organizer.setRole(Role.ORGANIZER);
        organizer.setPoints(50);
        organizer.setEventCount(1);
        organizer.setAvatarUri("img/org.png");
        organizer.setTeam(team);
        userRepository.save(organizer);

        // Пользователь
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Пользователь");
        user.setNickname("user01");
        user.setPhone("9990000003");
        user.setPassword(passwordEncoder.encode("userpass"));
        user.setRole(Role.USER);
        user.setPoints(20);
        user.setEventCount(2);
        user.setAvatarUri("img/user.png");
        user.setTeam(team);
        userRepository.save(user);

        // Событие
        Event event = new Event();
        event.setId(UUID.randomUUID());
        event.setTitle("Субботник в парке");
        event.setDescription("Уборка территории, покраска скамеек и посадка деревьев");
        event.setLocationName("Парк Победы");
        event.setLatitude(55.751244);
        event.setLongitude(37.618423);
        event.setDateTime("2025-05-10T10:00");
        event.setCreator(organizer);
        event.setFavorite(false);
        event.setFinished(false);
        event.setCompleted(true);
        event.setVerified(false);
        event.setRejected(false);
        event.setParticipantCount(3);
        event.setConfirmationComment("Чисто");
        event.setImageUri(List.of("img/clean1.jpg", "img/clean2.jpg", "img/clean3.jpg"));
        event.setTeam(team);
        eventRepository.save(event);

        // Достижение
        Achievement achievement = new Achievement();
        achievement.setId(UUID.randomUUID());
        achievement.setTitle("Первый шаг");
        achievement.setDescription("Участвуй в первом субботнике");
        achievement.setImageResId(1);
        achievementRepository.save(achievement);
    }
}
