package org.example.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user_achievement_cross_ref")
@IdClass(UserAchievementKey.class)
public class UserAchievementCrossRef {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @Column(name = "achievement_id")
    private UUID achievementId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "achievement_id", insertable = false, updatable = false)
    private Achievement achievement;

    public UserAchievementCrossRef(UUID userId, UUID achievementId, User user, Achievement achievement) {
        this.userId = userId;
        this.achievementId = achievementId;
        this.user = user;
        this.achievement = achievement;
    }

    public UserAchievementCrossRef() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(UUID achievementId) {
        this.achievementId = achievementId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }
}
