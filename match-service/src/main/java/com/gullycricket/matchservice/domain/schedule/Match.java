package com.gullycricket.matchservice.domain.schedule;

public class Match {

    private int match_id;
    private int series_id;
    private String series_name;
    private String data_path;
    private int alerts;
    private Header header;
    private Venue venue;
    private String team1_name;
    private String team2_name;
    private Team team1;
    private Team team2;


    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public int getSeries_id() {
        return series_id;
    }

    public void setSeries_id(int series_id) {
        this.series_id = series_id;
    }

    public String getSeries_name() {
        return series_name;
    }

    public void setSeries_name(String series_name) {
        this.series_name = series_name;
    }

    public String getData_path() {
        return data_path;
    }

    public void setData_path(String data_path) {
        this.data_path = data_path;
    }

    public int getAlerts() {
        return alerts;
    }

    public void setAlerts(int alerts) {
        this.alerts = alerts;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getTeam1_name() {
        return team1_name;
    }

    public void setTeam1_name(String team1_name) {
        this.team1_name = team1_name;
    }

    public String getTeam2_name() {
        return team2_name;
    }

    public void setTeam2_name(String team2_name) {
        this.team2_name = team2_name;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }


    public Match() {
    }

    public Match(int match_id, int series_id, String series_name, String data_path, int alerts, Header header, Venue venue, String team1_name, String team2_name, Team team1, Team team2) {
        this.match_id = match_id;
        this.series_id = series_id;
        this.series_name = series_name;
        this.data_path = data_path;
        this.alerts = alerts;
        this.header = header;
        this.venue = venue;
        this.team1_name = team1_name;
        this.team2_name = team2_name;
        this.team1 = team1;
        this.team2 = team2;

    }

    @Override
    public String toString() {
        return "Match{" +
                "match_id=" + match_id +
                ", series_id=" + series_id +
                ", series_name='" + series_name + '\'' +
                ", data_path='" + data_path + '\'' +
                ", alerts=" + alerts +
                ", header=" + header +
                ", venue=" + venue +
                ", team1_name='" + team1_name + '\'' +
                ", team2_name='" + team2_name + '\'' +
                ", team1=" + team1 +
                ", team2=" + team2 +
                '}';
    }
}
