/*
 * Copyright (c) 2023.
 * Created this for the project called "TheJackFolio"
 * All right reserved by Jack
 */

package com.esportarena.microservices.esportsarenaapi.models;

import com.esportarena.microservices.esportsarenaapi.enums.GameStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Game {

    private String name;
    private GameStatus status;
    private String message;

    public Game() {
    }

    public Game(String name, GameStatus status, String message) {
        this.name = name;
        this.status = status;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
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
        Game game = (Game) o;
        return Objects.equals(name, game.name) && status == game.status && Objects.equals(message, game.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, status, message);
    }

    @Override
    public String toString() {
        return "Game{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
