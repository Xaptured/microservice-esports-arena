/*
 * Copyright (c) 2023.
 * Created this for the project called "TheJackFolio"
 * All right reserved by Jack
 */

package com.esportarena.microservices.esportsarenaapi.models;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Leaderboard {

    private Integer eventId;
    private Integer teamId;
    private String message;

    public Leaderboard() {
    }

    public Leaderboard(Integer eventId, Integer teamId, String message) {
        this.eventId = eventId;
        this.teamId = teamId;
        this.message = message;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Leaderboard that = (Leaderboard) o;
        return Objects.equals(eventId, that.eventId) && Objects.equals(teamId, that.teamId) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, teamId, message);
    }

    @Override
    public String toString() {
        return "Leaderboard{" +
                ", eventId=" + eventId +
                ", teamId=" + teamId +
                ", message='" + message + '\'' +
                '}';
    }
}
