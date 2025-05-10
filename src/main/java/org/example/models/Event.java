package org.example.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "events")
public class Event {
    @Id
    private UUID id;

    private String title;
    private String description;
    private String locationName;
    private double latitude;
    private double longitude;
    private String dateTime;
    private boolean isFavorite;
    private boolean isFinished;
    @ElementCollection
    private List<String> imageUri;
    private int participantCount;
    @ElementCollection
    private List<String> participant;
    @Column(nullable = false)
    private boolean rejected = false;

    private boolean completed; // Организатор отметил как завершённое
    private boolean verified;  // Админ проверил

    private String confirmationComment;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "event")
    private Set<UserEventCrossRef> userRefs;

    public Event(UUID id, String title, String description, String locationName, double latitude, double longitude, String dateTime, boolean isFavorite, boolean isFinished, List<String> imageUri, int participantCount, List<String> participant, boolean rejected, boolean completed, boolean verified, String confirmationComment, User creator, Team team, Set<UserEventCrossRef> userRefs) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateTime = dateTime;
        this.isFavorite = isFavorite;
        this.isFinished = isFinished;
        this.imageUri = imageUri;
        this.participantCount = participantCount;
        this.participant = participant;
        this.rejected = rejected;
        this.completed = completed;
        this.verified = verified;
        this.confirmationComment = confirmationComment;
        this.creator = creator;
        this.team = team;
        this.userRefs = userRefs;
    }

    public List<String> getParticipant() {
        return participant;
    }

    public void setParticipant(List<String> participant) {
        this.participant = participant;
    }

    public Event() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public List<String> getImageUri() {
        return imageUri;
    }

    public void setImageUri(List<String> imageUri) {
        this.imageUri = imageUri;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Set<UserEventCrossRef> getUserRefs() {
        return userRefs;
    }

    public void setUserRefs(Set<UserEventCrossRef> userRefs) {
        this.userRefs = userRefs;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }
}