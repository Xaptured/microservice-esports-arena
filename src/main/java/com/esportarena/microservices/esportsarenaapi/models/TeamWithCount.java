/*
 * Copyright (c) 2024.
 * Created this for the project called "TheJackFolio"
 * All right reserved by Jack
 */

package com.esportarena.microservices.esportsarenaapi.models;

import java.util.Objects;

public class TeamWithCount {

    private String teamName;
    private Integer remainingPlayers;
    private boolean isApproved;

    public TeamWithCount() {
    }

    public TeamWithCount(String teamName, Integer remainingPlayers, boolean isApproved) {
        this.teamName = teamName;
        this.remainingPlayers = remainingPlayers;
        this.isApproved = isApproved;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getRemainingPlayers() {
        return remainingPlayers;
    }

    public void setRemainingPlayers(Integer remainingPlayers) {
        this.remainingPlayers = remainingPlayers;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamWithCount that = (TeamWithCount) o;
        return isApproved == that.isApproved && Objects.equals(teamName, that.teamName) && Objects.equals(remainingPlayers, that.remainingPlayers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamName, remainingPlayers, isApproved);
    }

    @Override
    public String toString() {
        return "TeamWithCount{" +
                "teamName='" + teamName + '\'' +
                ", remainingPlayers=" + remainingPlayers +
                ", isApproved=" + isApproved +
                '}';
    }
}
