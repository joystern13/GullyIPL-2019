package com.gullyipl.matchservice.domain.schedule;

import java.util.List;
import java.util.Objects;

public class Schedule {
    Url url;
    List<Match> matches;

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public Schedule() {
    }

    public Schedule(Url url, List<Match> matches) {
        this.url = url;
        this.matches = matches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(url, schedule.url) &&
                Objects.equals(matches, schedule.matches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, matches);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "url=" + url +
                ", matches=" + matches +
                '}';
    }
}
