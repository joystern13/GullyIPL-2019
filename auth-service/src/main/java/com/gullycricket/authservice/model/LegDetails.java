package com.gullycricket.authservice.model;

import javax.persistence.*;

@Entity
@Table(name = "leg_details")
public class LegDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long legId;

    @Column
    private String legName;

    @Column
    private int isVotingMandatory;

    @Column
    private int isMomMandatory;

    public Long getLegId() {
        return legId;
    }

    public void setLegId(Long legId) {
        this.legId = legId;
    }

    public String getLegName() {
        return legName;
    }

    public void setLegName(String legName) {
        this.legName = legName;
    }

    public int getIsVotingMandatory() {
        return isVotingMandatory;
    }

    public void setIsVotingMandatory(int isVotingMandatory) {
        this.isVotingMandatory = isVotingMandatory;
    }

    public int getIsMomMandatory() {
        return isMomMandatory;
    }

    public void setIsMomMandatory(int isMomMandatory) {
        this.isMomMandatory = isMomMandatory;
    }

    public LegDetails(Long legId, String legName, int isVotingMandatory, int isMomMandatory) {
        this.legId = legId;
        this.legName = legName;
        this.isVotingMandatory = isVotingMandatory;
        this.isMomMandatory = isMomMandatory;
    }

    @Override
    public String toString() {
        return "LegDetails{" +
                "legId=" + legId +
                ", legName='" + legName + '\'' +
                ", isVotingMandatory=" + isVotingMandatory +
                ", isMomMandatory=" + isMomMandatory +
                '}';
    }
}
