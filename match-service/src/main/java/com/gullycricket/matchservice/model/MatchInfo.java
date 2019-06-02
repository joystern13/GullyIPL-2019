package com.gullycricket.matchservice.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class MatchInfo {
    @Id
    @Column(name = "match_id")
    private int matchId;

    @Column(name = "match_desc")
    private String matchDescription;

    @Column(name = "start_time")
    private long startTime;

    @Column(name = "end_time")
    private long endTime;

    @Column(name = "day_night_flag")
    private int dayNightFlag;

    @Column(name = "venue_name")
    private String venueName;

    @Column(name="venue_location")
    private String venueLocation;

    @Column(name="match_state")
    private String matchState;

    @Column(name="result_description")
    private String resultDesc;

    @ManyToOne
    @JoinColumn(name="team1_id")
    private TeamInfo team1Info;

    @ManyToOne
    @JoinColumn(name="team2_id")
    private TeamInfo team2Info;

    @Column(name = "winner_team_id")
    private int winnerTeamId;

    @Column(name = "points")
    private BigDecimal points;

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getMatchDescription() {
        return matchDescription;
    }

    public void setMatchDescription(String matchDescription) {
        this.matchDescription = matchDescription;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getDayNightFlag() {
        return dayNightFlag;
    }

    public void setDayNightFlag(int dayNightFlag) {
        this.dayNightFlag = dayNightFlag;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueLocation() {
        return venueLocation;
    }

    public void setVenueLocation(String venueLocation) {
        this.venueLocation = venueLocation;
    }

    public String getMatchState() {
        return matchState;
    }

    public void setMatchState(String matchState) {
        this.matchState = matchState;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public TeamInfo getTeam1Info() {
        return team1Info;
    }

    public void setTeam1Info(TeamInfo team1Info) {
        this.team1Info = team1Info;
    }

    public TeamInfo getTeam2Info() {
        return team2Info;
    }

    public void setTeam2Info(TeamInfo team2Info) {
        this.team2Info = team2Info;
    }

    public int getWinnerTeamId() {
        return winnerTeamId;
    }

    public void setWinnerTeamId(int winnerTeamId) {
        this.winnerTeamId = winnerTeamId;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    public MatchInfo() {
    }

    public MatchInfo(int matchId, String matchDescription, long startTime, long endTime, int dayNightFlag, String venueName, String venueLocation, String matchState, String resultDesc, TeamInfo team1Info, TeamInfo team2Info, int winnerTeamId, BigDecimal points) {
        this.matchId = matchId;
        this.matchDescription = matchDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayNightFlag = dayNightFlag;
        this.venueName = venueName;
        this.venueLocation = venueLocation;
        this.matchState = matchState;
        this.resultDesc = resultDesc;
        this.team1Info = team1Info;
        this.team2Info = team2Info;
        this.winnerTeamId = winnerTeamId;
        this.points = points;
    }
}
