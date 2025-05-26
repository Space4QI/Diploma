package org.example.Dto;

public class EcoHeroDTO {
    private String nickname;
    private int points;
    private int events;

    public EcoHeroDTO(String nickname, int points, int events) {
        this.nickname = nickname;
        this.points = points;
        this.events = events;
    }

    public EcoHeroDTO() {}

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getEvents() {
        return events;
    }

    public void setEvents(int events) {
        this.events = events;
    }
}
