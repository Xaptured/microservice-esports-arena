/*
 * Copyright (c) 2024.
 * Created this for the project called "TheJackFolio"
 * All right reserved by Jack
 */

package com.esportarena.microservices.esportsarenaapi.models;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TeamWithID {

    private String teamName;
    private Integer teamID;

    public TeamWithID() {
    }

    public TeamWithID(String teamName, Integer teamID) {
        this.teamName = teamName;
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTeamID() {
        return teamID;
    }

    public void setTeamID(Integer teamID) {
        this.teamID = teamID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamWithID that = (TeamWithID) o;
        return Objects.equals(teamName, that.teamName) && Objects.equals(teamID, that.teamID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamName, teamID);
    }

    @Override
    public String toString() {
        return "TeamWithID{" +
                "teamName='" + teamName + '\'' +
                ", teamID=" + teamID +
                '}';
    }
}
