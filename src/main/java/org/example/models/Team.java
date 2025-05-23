package org.example.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teams")
public class Team extends BaseEntity {

    private String name;
    private int color;
    @Column(length = 10000)
    private String areaPoints;
    private int points;

    @JsonIgnore
    @OneToMany(mappedBy = "team")
    private Set<User> users;

    @ManyToMany(mappedBy = "teams")
    private Set<Event> events = new HashSet<>();


    public Team(String name, int color, String areaPoints, int points, Set<User> users, Set<Event> events) {
        this.name = name;
        this.color = color;
        this.areaPoints = areaPoints;
        this.points = points;
        this.users = users;
        this.events = events;
    }

    public Team(String name, int color, String areaPoints) {
        this.name = name;
        this.color = color;
        this.areaPoints = areaPoints;
        this.points = 0;
        this.users = new HashSet<>();
        this.events = new HashSet<>();
    }

    public Team() {
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}