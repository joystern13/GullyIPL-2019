package com.gullycricket.matchservice.domain.schedule;

public class Url {
    private String match;

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public Url() {
    }

    public Url(String match) {
        this.match = match;
    }
}
