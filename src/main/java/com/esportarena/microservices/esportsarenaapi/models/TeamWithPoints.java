/*
 * Copyright (c) 2024.
 * Created this for the project called "TheJackFolio"
 * All right reserved by Jack
 */

package com.esportarena.microservices.esportsarenaapi.models;

import java.util.Objects;

public class TeamWithPoints {

    private String teamName;
    private Double points;

    public TeamWithPoints() {
    }

    public TeamWithPoints(String teamName, Double points) {
        this.teamName = teamName;
        this.points = points;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamWithPoints that = (TeamWithPoints) o;
        return Objects.equals(teamName, that.teamName) && Objects.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamName, points);
    }

    @Override
    public String toString() {
        return "TeamWithPoints{" +
                "teamName='" + teamName + '\'' +
                ", points=" + points +
                '}';
    }
}
