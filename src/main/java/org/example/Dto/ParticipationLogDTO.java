package org.example.Dto;

import java.time.LocalDateTime;

public class ParticipationLogDTO {
    private String userName;
    private String eventTitle;
    private LocalDateTime eventTime;

    public ParticipationLogDTO(String userName, String eventTitle, java.sql.Timestamp timestamp) {
        this.userName = userName;
        this.eventTitle = eventTitle;
        this.eventTime = timestamp != null ? timestamp.toLocalDateTime() : null;
    }

    public ParticipationLogDTO() {}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }
}