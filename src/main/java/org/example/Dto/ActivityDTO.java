package org.example.Dto;

public class ActivityDTO {
    private String message;
    private String timestamp;

    public ActivityDTO(String message, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}