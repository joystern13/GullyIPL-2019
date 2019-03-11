package com.gullyipl.votingservice.model;

import javax.persistence.*;
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
}
