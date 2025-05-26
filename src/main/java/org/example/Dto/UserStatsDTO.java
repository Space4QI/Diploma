package org.example.Dto;

public class UserStatsDTO {
    private String fullName;
    private Long participationCount;

    public UserStatsDTO(String fullName, Long participationCount) {
        this.fullName = fullName;
        this.participationCount = participationCount;
    }

    public UserStatsDTO() {}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getParticipationCount() {
        return participationCount;
    }

    public void setParticipationCount(Long participationCount) {
        this.participationCount = participationCount;
    }
}
