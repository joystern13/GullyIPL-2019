package com.gullyipl.matchservice.model;

import javax.persistence.*;

@Entity
public class TeamInfo {
    @Id
    @Column(name = "team_id")
    private int teamId;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "team_code")
    private String teamCode;

    @Column(name = "captain")
    private String captain;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getCaptain() {
        return captain;
    }

    public void setCaptain(String captain) {
        this.captain = captain;
    }
}
