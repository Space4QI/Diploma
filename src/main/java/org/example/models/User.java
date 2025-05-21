package org.example.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

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
    private LocalDateTime joinedTeamAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "user")
    private Set<UserEventCrossRef> eventCrossRefs;

    @OneToMany(mappedBy = "user")
    private Set<UserAchievementCrossRef> achievementRefs;

    public User(Role role, String name, String nickname, String phone, String password, int points, int eventCount, String avatarUri, LocalDateTime joinedTeamAt, Team team, Set<UserEventCrossRef> eventCrossRefs, Set<UserAchievementCrossRef> achievementRefs) {
        this.role = role;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.password = password;
        this.points = points;
        this.eventCount = eventCount;
        this.avatarUri = avatarUri;
        this.joinedTeamAt = joinedTeamAt;
        this.team = team;
        this.eventCrossRefs = eventCrossRefs;
        this.achievementRefs = achievementRefs;
    }

    public User() {
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

    public LocalDateTime getJoinedTeamAt() {
        return joinedTeamAt;
    }

    public void setJoinedTeamAt(LocalDateTime joinedTeamAt) {
        this.joinedTeamAt = joinedTeamAt;
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