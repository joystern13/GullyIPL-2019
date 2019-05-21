package com.gullycricket.authservice.model;

import javax.persistence.*;

@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userDetId;

    @Column
    private Long userId;

    @Column
    private Long legId;

    @Column
    private Long points;

    @Column
    private Long momPoints;

    @Column
    private Long deposit;

    @Column
    private Long balance;

    public Long getUserDetId() {
        return userDetId;
    }

    public void setUserDetId(Long userDetId) {
        this.userDetId = userDetId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLegId() {
        return legId;
    }

    public void setLegId(Long legId) {
        this.legId = legId;
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

    public Long getDeposit() {
        return deposit;
    }

    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public UserDetails(Long userDetId, Long userId, Long legId, Long points, Long momPoints, Long deposit, Long balance) {
        this.userDetId = userDetId;
        this.userId = userId;
        this.legId = legId;
        this.points = points;
        this.momPoints = momPoints;
        this.deposit = deposit;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userDetId=" + userDetId +
                ", userId=" + userId +
                ", legId=" + legId +
                ", points=" + points +
                ", momPoints=" + momPoints +
                ", deposit=" + deposit +
                ", balance=" + balance +
                '}';
    }
}
