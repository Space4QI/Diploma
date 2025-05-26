package org.example.Dto;

public class TeamStatsDTO {
    private String teamName;
    private Long userCount;
    private Long participationCount;

    public TeamStatsDTO(String teamName, Long userCount, Long participationCount) {
        this.teamName = teamName;
        this.userCount = userCount;
        this.participationCount = participationCount;
    }

    public TeamStatsDTO() {}

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public Long getParticipationCount() {
        return participationCount;
    }

    public void setParticipationCount(Long participationCount) {
        this.participationCount = participationCount;
    }
}