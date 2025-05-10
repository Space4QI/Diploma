package org.example.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String name;
    private String nickname;
    @Column(unique = true)
    private String phone;
    private String password;
    private int points;
    private int eventCount;
    private String avatarUri;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "user")
    private Set<UserEventCrossRef> eventCrossRefs;

    @OneToMany(mappedBy = "user")
    private Set<UserAchievementCrossRef> achievementRefs;

    public User(UUID id, String name, Role role, String nickname, String phone, String password, int points, int eventCount, String avatarUri, Team team, Set<UserEventCrossRef> eventCrossRefs, Set<UserAchievementCrossRef> achievementRefs) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.points = points;
        this.eventCount = eventCount;
        this.avatarUri = avatarUri;
        this.team = team;
        this.eventCrossRefs = eventCrossRefs;
        this.achievementRefs = achievementRefs;
    }

    public User() {}

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getEventCount() {
        return eventCount;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Set<UserAchievementCrossRef> getAchievementRefs() {
        return achievementRefs;
    }

    public void setAchievementRefs(Set<UserAchievementCrossRef> achievementRefs) {
        this.achievementRefs = achievementRefs;
    }

    public Set<UserEventCrossRef> getEventCrossRefs() {
        return eventCrossRefs;
    }

    public void setEventCrossRefs(Set<UserEventCrossRef> eventCrossRefs) {
        this.eventCrossRefs = eventCrossRefs;
    }
}