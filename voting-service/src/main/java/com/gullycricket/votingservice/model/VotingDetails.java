package com.gullycricket.votingservice.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class VotingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_vote_id")
    private int userVoteId;

    @Column(name="user_id")
    private int userId;

    @Column(name = "match_id")
    private int matchId;

    @Column(name = "team_id")
    private int teamId;

    @Column(name = "points")
    private BigDecimal points;

    @Column(name = "voting_date")
    private Date votingDate = new Date();

    public int getUserVoteId() {
        return userVoteId;
    }

    public void setUserVoteId(int userVoteId) {
        this.userVoteId = userVoteId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public Date getVotingDate() {
        return votingDate;
    }

    public void setVotingDate(Date votingDate) {
        this.votingDate = votingDate;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    public VotingDetails(int userVoteId, int userId, int matchId, int teamId, BigDecimal points, Date votingDate) {
        this.userVoteId = userVoteId;
        this.userId = userId;
        this.matchId = matchId;
        this.teamId = teamId;
        this.points = points;
        this.votingDate = votingDate;
    }

    @Override
    public String toString() {
        return "VotingDetails{" +
                "userVoteId=" + userVoteId +
                ", userId=" + userId +
                ", matchId=" + matchId +
                ", teamId=" + teamId +
                ", points=" + points +
                ", votingDate=" + votingDate +
                '}';
    }
}
