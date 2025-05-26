package org.example.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "achievements")
public class Achievement extends BaseEntity {

    private String title;
    private String description;
    private int imageResId;

    @OneToMany(mappedBy = "achievement")
    private Set<UserAchievementCrossRef> userRefs;

    public Achievement(String title, String description, int imageResId, Set<UserAchievementCrossRef> userRefs) {
        super();
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
        this.userRefs = userRefs;
    }

    public Achievement() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public Set<UserAchievementCrossRef> getUserRefs() {
        return userRefs;
    }

    public void setUserRefs(Set<UserAchievementCrossRef> userRefs) {
        this.userRefs = userRefs;
    }
}
