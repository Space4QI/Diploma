package org.example.Dto;

import java.util.UUID;

public class TeamDTO {
    private UUID id;
    private String name;
    private int color;
    private String areaPoints;
    private int points;

    public TeamDTO(UUID id, String name, int color, String areaPoints, int points) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.areaPoints = areaPoints;
        this.points = points;
    }

    public TeamDTO() {}


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getAreaPoints() {
        return areaPoints;
    }

    public void setAreaPoints(String areaPoints) {
        this.areaPoints = areaPoints;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
