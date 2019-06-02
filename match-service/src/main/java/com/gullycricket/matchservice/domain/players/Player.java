package com.gullycricket.matchservice.domain.players;

public class Player {
    private int id;
    private String f_name;
    private String name;
    private String age;
    private String image;
    private String role;
    private String style;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", f_name='" + f_name + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", image='" + image + '\'' +
                ", role='" + role + '\'' +
                ", style='" + style + '\'' +
                '}';
    }
}
