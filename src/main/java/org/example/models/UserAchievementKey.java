package org.example.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class UserAchievementKey implements Serializable {
    private UUID userId;
    private UUID achievementId;

    public UserAchievementKey(UUID userId, UUID achievementId) {
        this.userId = userId;
        this.achievementId = achievementId;
    }

    public UserAchievementKey() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAchievementKey)) return false;
        UserAchievementKey that = (UserAchievementKey) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(achievementId, that.achievementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, achievementId);
    }
}
