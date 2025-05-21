package org.example.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_event_cross_ref")
public class UserEventCrossRef {

    @EmbeddedId
    private UserEventKey id = new UserEventKey();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    public UserEventCrossRef(User user, Event event) {
        this.user = user;
        this.event = event;
        this.id = new UserEventKey(user.getId(), event.getId());
    }

    public UserEventCrossRef(User user, Event event, LocalDateTime joinedAt) {
        this.user = user;
        this.event = event;
        this.id = new UserEventKey(user.getId(), event.getId());
        this.joinedAt = joinedAt;
    }

    public UserEventCrossRef() {}

    public UserEventKey getId() {
        return id;
    }

    public void setId(UserEventKey id) {
        this.id = id;
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

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}
