package org.example.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class TeamDTO implements Serializable {
    private UUID id;

    @NotBlank(message = "Team name is required")
    @Size(min = 2, max = 50, message = "Team name must be 2–50 characters")
    private String name;

    @Min(value = 0, message = "Color code must be a positive number")
    private int color;

    @NotBlank(message = "Area points must not be empty")
    private String areaPoints;

    @Min(value = 0, message = "Points cannot be negative")
    private int points;

    private List<UUID> eventIds;

    public TeamDTO(UUID id, String name, int color, String areaPoints, int points, List<UUID> eventIds) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.areaPoints = areaPoints;
        this.points = points;
        this.eventIds = eventIds;
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

    public List<UUID> getEventIds() {
        return eventIds;
    }

    public void setEventIds(List<UUID> eventIds) {
        this.eventIds = eventIds;
    }
}
