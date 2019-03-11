package com.gullyipl.matchservice.domain.teams;

import java.util.List;

public class Teams {
    private Series series;
    private List<Team> teams;

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
