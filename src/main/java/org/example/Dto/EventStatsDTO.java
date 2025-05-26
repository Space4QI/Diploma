package org.example.Dto;

import java.time.LocalDateTime;

public class EventStatsDTO {
    private String eventName;
    private LocalDateTime date;
    private Long participantCount;

    public EventStatsDTO(String eventName, LocalDateTime date, Long participantCount) {
        this.eventName = eventName;
        this.date = date;
        this.participantCount = participantCount;
    }

    public EventStatsDTO() {}

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(Long participantCount) {
        this.participantCount = participantCount;
    }
}
