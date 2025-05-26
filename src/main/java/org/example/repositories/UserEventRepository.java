package org.example.repositories;

import org.example.Dto.*;
import org.example.models.Event;
import org.example.models.User;
import org.example.models.UserEventCrossRef;
import org.example.models.UserEventKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserEventRepository extends JpaRepository<UserEventCrossRef, UserEventKey> {

    List<UserEventCrossRef> findByUserId(UUID userId);

    List<UserEventCrossRef> findByEventId(UUID eventId);

    void deleteByUserIdAndEventId(UUID userId, UUID eventId);

    boolean existsByUserAndEvent(User user, Event event);

    long countByUser_Id(UUID userId);

    void deleteByUserAndEvent(User user, Event event);

    @Query("SELECT u FROM UserEventCrossRef u JOIN FETCH u.event JOIN FETCH u.user")
    List<UserEventCrossRef> findAllWithUserAndEvent();

    @Query("SELECT u FROM UserEventCrossRef u " +
            "WHERE FUNCTION('to_timestamp', u.event.dateTime, 'YYYY-MM-DD\"T\"HH24:MI:SS') BETWEEN :start AND :end")
    List<UserEventCrossRef> findByEventDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new org.example.Dto.UserStatsDTO(ue.user.name, COUNT(ue)) " +
            "FROM UserEventCrossRef ue " +
            "GROUP BY ue.user.name " +
            "ORDER BY COUNT(ue) DESC")
    List<UserStatsDTO> findTopUsers();

    @Query("SELECT new org.example.Dto.EventStatsDTO(e.title, e.completedAt, COUNT(ue)) " +
            "FROM UserEventCrossRef ue " +
            "JOIN ue.event e " +
            "GROUP BY e.id, e.title, e.completedAt " +
            "ORDER BY COUNT(ue) DESC")
    List<EventStatsDTO> findPopularEvents();

    @Query("SELECT new org.example.Dto.EventCountByDateDTO(CAST(e.completedAt AS date), COUNT(e)) " +
            "FROM Event e WHERE e.completed = true AND e.completedAt IS NOT NULL " +
            "GROUP BY CAST(e.completedAt AS date) " +
            "ORDER BY CAST(e.completedAt AS date)")
    List<EventCountByDateDTO> countEventsByDate();

    @Query("SELECT new org.example.Dto.UserStatsDTO(ue.user.name, COUNT(ue)) " +
            "FROM UserEventCrossRef ue " +
            "JOIN ue.event e " +
            "WHERE FUNCTION('to_timestamp', e.dateTime, 'YYYY-MM-DD\"T\"HH24:MI:SS') BETWEEN :start AND :end " +
            "GROUP BY ue.user.name ORDER BY COUNT(ue) DESC")
    List<UserStatsDTO> findTopUsersBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new org.example.Dto.EventStatsDTO(e.title, e.completedAt, COUNT(ue)) " +
            "FROM UserEventCrossRef ue " +
            "JOIN ue.event e " +
            "WHERE e.completedAt BETWEEN :start AND :end " +
            "GROUP BY e.id, e.title, e.completedAt ORDER BY COUNT(ue) DESC")
    List<EventStatsDTO> findPopularEventsBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new org.example.Dto.EventCountByDateDTO(CAST(e.completedAt AS date), COUNT(e)) " +
            "FROM Event e WHERE e.completed = true AND e.completedAt BETWEEN :start AND :end " +
            "GROUP BY CAST(e.completedAt AS date) ORDER BY CAST(e.completedAt AS date)")
    List<EventCountByDateDTO> countEventsByDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new org.example.Dto.TeamStatsDTO(t.name, COUNT(DISTINCT u), COUNT(ue)) " +
            "FROM UserEventCrossRef ue " +
            "JOIN ue.user u " +
            "JOIN u.team t " +
            "GROUP BY t.name ORDER BY COUNT(ue) DESC")
    List<TeamStatsDTO> getTopTeams();

    @Query("SELECT new org.example.Dto.ParticipationLogDTO(u.name, e.title, FUNCTION('to_timestamp', e.dateTime, 'YYYY-MM-DD\"T\"HH24:MI:SS')) " +
            "FROM UserEventCrossRef ue " +
            "JOIN ue.user u " +
            "JOIN ue.event e " +
            "ORDER BY e.dateTime DESC")
    List<ParticipationLogDTO> getParticipationLog();

}
