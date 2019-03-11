package com.gullyipl.matchservice.domain.schedule;

public class Team {
    private int id;
    private String name;
    private String s_name;
    private int flag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Team() {
    }

    public Team(int id, String name, String s_name, int flag) {
        this.id = id;
        this.name = name;
        this.s_name = s_name;
        this.flag = flag;
    }
}
