package org.example.Dto;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.*;

import java.util.UUID;
import java.util.List;

public class EventDTO {

    private UUID id;

    @NotBlank
    @Size(max = 100)
    private String title;

    @Size(max = 500)
    private String description;

    @NotBlank
    private String locationName;

    @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0")
    private double latitude;

    @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0")
    private double longitude;

    @NotBlank
    private String dateTime;

    @NotNull
    private UUID creatorId;

    private boolean isFavorite;

    private UUID teamId;

    private List<@NotBlank String> imageUri;

    private boolean verified;

    @Size(max = 300)
    private String confirmationComment;

    private boolean isFinished;

    private String teamName;

    @Min(0)
    private int participantCount;

    private List<@NotBlank String> participant;

    private boolean rejected;

    public EventDTO(UUID id, String title, String description, String locationName, double latitude, double longitude, String dateTime, UUID creatorId, boolean isFavorite, UUID teamId, List<String> imageUri, boolean verified, String confirmationComment, boolean isFinished, String teamName, int participantCount, List<String> participant, boolean rejected) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateTime = dateTime;
        this.creatorId = creatorId;
        this.isFavorite = isFavorite;
        this.teamId = teamId;
        this.imageUri = imageUri;
        this.verified = verified;
        this.confirmationComment = confirmationComment;
        this.isFinished = isFinished;
        this.teamName = teamName;
        this.participantCount = participantCount;
        this.participant = participant;
        this.rejected = rejected;
    }

    public EventDTO() {}

    public List<String> getParticipant() {
        return participant;
    }

    public void setParticipant(List<String> participant) {
        this.participant = participant;
    }

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

    public UUID getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId) {
        this.creatorId = creatorId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }

    public List<String> getImageUri() {
        return imageUri;
    }

    public void setImageUri(List<String> imageUri) {
        this.imageUri = imageUri;
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

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }
}
