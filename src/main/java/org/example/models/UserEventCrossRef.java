package org.example.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user_event_cross_ref")
@IdClass(UserEventKey.class)
public class UserEventCrossRef {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @Column(name = "event_id")
    private UUID eventId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", insertable = false, updatable = false)
    private Event event;

    public UserEventCrossRef(UUID userId, UUID eventId, User user, Event event) {
        this.userId = userId;
        this.eventId = eventId;
        this.user = user;
        this.event = event;
    }

    public UserEventCrossRef() {}

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}