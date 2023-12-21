/*
 * Copyright (c) 2023.
 * Created this for the project called "TheJackFolio"
 * All right reserved by Jack
 */

package com.esportarena.microservices.esportsarenaapi.models;

import java.util.Objects;

public class TeamDetail {

    private String email;
    private Integer playerNumber;

    public TeamDetail() {
    }

    public TeamDetail(String email, Integer playerNumber) {
        this.email = email;
        this.playerNumber = playerNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamDetail that = (TeamDetail) o;
        return Objects.equals(email, that.email) && Objects.equals(playerNumber, that.playerNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, playerNumber);
    }

    @Override
    public String toString() {
        return "TeamDetail{" +
                "email='" + email + '\'' +
                ", playerNumber=" + playerNumber +
                '}';
    }
}
