package org.example.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "events")
public class Event extends BaseEntity {

    private String title;
    private String description;
    private String locationName;
    private double latitude;
    private double longitude;
    private String dateTime;
    private boolean isFavorite;
    @ElementCollection
    private List<String> imageUri;
    private int participantCount;
    @ElementCollection
    private List<String> participant;
    @Column(nullable = false)
    private boolean rejected = false;

    private boolean completed;

    private LocalDateTime completedAt;
    private boolean verified;

    private String confirmationComment;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToMany
    @JoinTable(
            name = "event_team",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams = new HashSet<>();


    @OneToMany(mappedBy = "event")
    private Set<UserEventCrossRef> userRefs;

    public Event(String title, String description, String locationName, double latitude, double longitude, String dateTime, boolean isFavorite, List<String> imageUri, int participantCount, List<String> participant, boolean rejected, boolean completed, LocalDateTime completedAt, boolean verified, String confirmationComment, User creator, Set<Team> teams, Set<UserEventCrossRef> userRefs) {
        this.title = title;
        this.description = description;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateTime = dateTime;
        this.isFavorite = isFavorite;
        this.imageUri = imageUri;
        this.participantCount = participantCount;
        this.participant = participant;
        this.rejected = rejected;
        this.completed = completed;
        this.completedAt = completedAt;
        this.verified = verified;
        this.confirmationComment = confirmationComment;
        this.creator = creator;
        this.teams = teams;
        this.userRefs = userRefs;
    }

    public Event() {
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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public List<String> getImageUri() {
        return imageUri;
    }

    public void setImageUri(List<String> imageUri) {
        this.imageUri = imageUri;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

    public List<String> getParticipant() {
        return participant;
    }

    public void setParticipant(List<String> participant) {
        this.participant = participant;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getConfirmationComment() {
        return confirmationComment;
    }

    public void setConfirmationComment(String confirmationComment) {
        this.confirmationComment = confirmationComment;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Set<UserEventCrossRef> getUserRefs() {
        return userRefs;
    }

    public void setUserRefs(Set<UserEventCrossRef> userRefs) {
        this.userRefs = userRefs;
    }
}