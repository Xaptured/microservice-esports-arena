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

    public TeamWithCount() {
    }

    public TeamWithCount(String teamName, Integer remainingPlayers) {
        this.teamName = teamName;
        this.remainingPlayers = remainingPlayers;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamWithCount that = (TeamWithCount) o;
        return Objects.equals(teamName, that.teamName) && Objects.equals(remainingPlayers, that.remainingPlayers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamName, remainingPlayers);
    }

    @Override
    public String toString() {
        return "TeamWithCount{" +
                "teamName='" + teamName + '\'' +
                ", remainingPlayers=" + remainingPlayers +
                '}';
    }
}
