package org.example.repositories;

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
}
