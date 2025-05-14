package org.example.Dto;

import jakarta.validation.constraints.*;

import java.util.UUID;
import java.util.List;

public class EventDTO {
    private static final long serialVersionUID = 1L;

    private UUID id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be at most 100 characters")
    private String title;

    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;

    @NotBlank(message = "Location name is required")
    @Size(max = 100, message = "Location name must be at most 100 characters")
    private String locationName;

    @DecimalMin(value = "-90.0", message = "Latitude must be ≥ -90.0")
    @DecimalMax(value = "90.0", message = "Latitude must be ≤ 90.0")
    private double latitude;

    @DecimalMin(value = "-180.0", message = "Longitude must be ≥ -180.0")
    @DecimalMax(value = "180.0", message = "Longitude must be ≤ 180.0")
    private double longitude;

    @NotBlank(message = "Date and time are required")
    private String dateTime;

    @NotNull(message = "Creator ID is required")
    private UUID creatorId;

    private boolean isFavorite;

    private UUID teamId;

    @Size(max = 10, message = "No more than 10 images allowed")
    private List<@NotBlank(message = "Image URI must not be blank") String> imageUri;

    private boolean verified;

    @Size(max = 300, message = "Confirmation comment must be at most 300 characters")
    private String confirmationComment;

    private boolean isFinished;

    private String teamName;

    @Min(value = 0, message = "Participant count cannot be negative")
    private int participantCount;

    private List<@NotBlank(message = "Participant name must not be blank") String> participant;

    private boolean rejected;

    public EventDTO(UUID id, String title, String description, String locationName, double latitude, double longitude, String dateTime, UUID creatorId, boolean isFavorite, UUID teamId, List<@NotBlank(message = "Image URI must not be blank") String> imageUri, boolean verified, String confirmationComment, boolean isFinished, String teamName, int participantCount, List<@NotBlank(message = "Participant name must not be blank") String> participant, boolean rejected) {
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
