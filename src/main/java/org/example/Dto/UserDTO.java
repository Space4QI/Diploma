package org.example.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.example.models.Role;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be 2–50 characters")
    private String name;

    @NotBlank(message = "Nickname is required")
    @Size(min = 3, max = 30, message = "Nickname must be 3–30 characters")
    private String nickname;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "\\d{10,15}", message = "Phone must be 10–15 digits")
    private String phone;

    private Role role;

    @Min(value = 0, message = "Points cannot be negative")
    private int points;

    @Min(value = 0, message = "Event count cannot be negative")
    private int eventCount;

    @Size(max = 255, message = "Avatar URI too long")
    private String avatarUri;

    private LocalDateTime joinedTeamAt;

    private UUID teamId;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "Password must contain uppercase, lowercase letters and a digit"
    )
    private String password;

    private List<AchievementDTO> achievements;

    public UserDTO(UUID id, String name, String nickname, String phone, Role role, int points, int eventCount, String avatarUri, LocalDateTime joinedTeamAt, UUID teamId, String password, List<AchievementDTO> achievements) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.role = role;
        this.points = points;
        this.eventCount = eventCount;
        this.avatarUri = avatarUri;
        this.joinedTeamAt = joinedTeamAt;
        this.teamId = teamId;
        this.password = password;
        this.achievements = achievements;
    }

    public UserDTO() {
    }

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

    public UUID getTeamId() {
        return teamId;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AchievementDTO> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<AchievementDTO> achievements) {
        this.achievements = achievements;
    }
}
