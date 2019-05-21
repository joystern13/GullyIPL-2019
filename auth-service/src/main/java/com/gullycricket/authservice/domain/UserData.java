package com.gullycricket.authservice.domain;

public class UserData {

    private Long id;

    private String name;

    private Long points;

    private Long momPoints;

    private String imageUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Long getMomPoints() {
        return momPoints;
    }

    public void setMomPoints(Long momPoints) {
        this.momPoints = momPoints;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserData() {
    }

    public UserData(Long id, String name, Long points, Long momPoints, String imageUrl) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.momPoints = momPoints;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", points='" + points + '\'' +
                ", momPoints='" + momPoints + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
