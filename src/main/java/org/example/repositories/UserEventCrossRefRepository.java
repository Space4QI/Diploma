package org.example.repositories;

import org.example.models.UserEventCrossRef;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface UserEventCrossRefRepository extends CrudRepository<UserEventCrossRef, UUID> {

    @Query("SELECT u FROM UserEventCrossRef u " +
            "WHERE FUNCTION('to_timestamp', u.event.dateTime, 'YYYY-MM-DD\"T\"HH24:MI:SS') BETWEEN :start AND :end")
    List<UserEventCrossRef> findByEventDateBetween(LocalDateTime start, LocalDateTime end);

    List<UserEventCrossRef> findAll();
}