package org.example.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class UserEventKey implements Serializable {
    private UUID userId;
    private UUID eventId;

    public UserEventKey(UUID userId, UUID eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    public UserEventKey() {}


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEventKey)) return false;
        UserEventKey that = (UserEventKey) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, eventId);
    }
}
