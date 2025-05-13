package org.example.models;

import jakarta.persistence.*;

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

    public UserEventCrossRef() {}

    public UserEventCrossRef(User user, Event event) {
        this.user = user;
        this.event = event;
    }

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
}
