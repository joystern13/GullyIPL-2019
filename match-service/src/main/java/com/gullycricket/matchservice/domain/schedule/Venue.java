package com.gullycricket.matchservice.domain.schedule;

public class Venue {
    private String name;
    private String location;
    private String timezone;
    private float lat;
    private float longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Venue() {
    }

    public Venue(String name, String location, String timezone, float lat, float longitude) {
        this.name = name;
        this.location = location;
        this.timezone = timezone;
        this.lat = lat;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", timezone='" + timezone + '\'' +
                ", lat=" + lat +
                ", longitude=" + longitude +
                '}';
    }
}
