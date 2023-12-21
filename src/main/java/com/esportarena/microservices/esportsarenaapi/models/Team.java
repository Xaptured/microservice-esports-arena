/*
 * Copyright (c) 2023.
 * Created this for the project called "TheJackFolio"
 * All right reserved by Jack
 */

package com.esportarena.microservices.esportsarenaapi.models;


import com.esportarena.microservices.esportsarenaapi.enums.TeamStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class Team {

    private String name;
    private Integer eventId;
    private TeamStatus teamStatus;
    private List<TeamDetail> detail;
    private String message;

    public Team() {
    }

    public Team(String name, Integer eventId, TeamStatus teamStatus, List<TeamDetail> detail, String message) {
        this.name = name;
        this.eventId = eventId;
        this.teamStatus = teamStatus;
        this.detail = detail;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public TeamStatus getTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(TeamStatus teamStatus) {
        this.teamStatus = teamStatus;
    }

    public List<TeamDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<TeamDetail> detail) {
        this.detail = detail;
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
        Team team = (Team) o;
        return Objects.equals(name, team.name) && Objects.equals(eventId, team.eventId) && teamStatus == team.teamStatus && Objects.equals(detail, team.detail) && Objects.equals(message, team.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, eventId, teamStatus, detail, message);
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", eventId=" + eventId +
                ", teamStatus=" + teamStatus +
                ", detail=" + detail +
                ", message='" + message + '\'' +
                '}';
    }
}
