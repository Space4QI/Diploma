package org.example.Dto;

import org.example.models.Role;

import java.io.Serializable;
import java.util.UUID;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String name;
    private String nickname;
    private String phone;
    private Role role;
    private int points;
    private int eventCount;
    private String avatarUri;
    private UUID teamId;
    private String password;

    public UserDTO(UUID id, String name, String nickname, String phone, Role role, int points, int eventCount, String avatarUri, UUID teamId, String password) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.role = role;
        this.points = points;
        this.eventCount = eventCount;
        this.avatarUri = avatarUri;
        this.teamId = teamId;
        this.password = password;
    }

    public UserDTO() {}

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
}
