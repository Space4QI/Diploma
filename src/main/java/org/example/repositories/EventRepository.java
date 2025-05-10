package org.example.repositories;

import org.example.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findByTeamId(UUID teamId);
    List<Event> findByCreatorId(UUID creatorId);

    List<Event> findByCompletedTrueAndVerifiedFalse();

    List<Event> findByVerifiedTrue();
}
