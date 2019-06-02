package com.gullycricket.matchservice.domain.schedule;

public class Header {
    private long start_time;
    private long end_time;
    private String match_desc;
    private String type;
    private int dn;
    private String state;
    private String state_title;
    private String status;
    private int winning_team_id;

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public String getMatch_desc() {
        return match_desc;
    }

    public void setMatch_desc(String match_desc) {
        this.match_desc = match_desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDn() {
        return dn;
    }

    public void setDn(int dn) {
        this.dn = dn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState_title() {
        return state_title;
    }

    public void setState_title(String state_title) {
        this.state_title = state_title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWinning_team_id() {
        return winning_team_id;
    }

    public void setWinning_team_id(int winning_team_id) {
        this.winning_team_id = winning_team_id;
    }


    public Header() {
    }

    public Header(long start_time, long end_time, String match_desc, String type, int dn, String state, String state_title, String status, int winning_team_id) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.match_desc = match_desc;
        this.type = type;
        this.dn = dn;
        this.state = state;
        this.state_title = state_title;
        this.status = status;
        this.winning_team_id = winning_team_id;
    }

    @Override
    public String toString() {
        return "Header{" +
                "start_time=" + start_time +
                ", end_time=" + end_time +
                ", match_desc='" + match_desc + '\'' +
                ", type='" + type + '\'' +
                ", dn=" + dn +
                ", state='" + state + '\'' +
                ", state_title='" + state_title + '\'' +
                ", status='" + status + '\'' +
                ", winning_team_id='" + winning_team_id + '\'' +
                '}';
    }
}
