/*
 * Copyright (c) 2023.
 * Created this for the project called "TheJackFolio"
 * All right reserved by Jack
 */

package com.esportarena.microservices.esportsarenaapi.models;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Viewer {

    private String email;
    private Integer eventId;
    private String message;

    public Viewer() {
    }

    public Viewer(String email, Integer eventId, String message) {
        this.email = email;
        this.eventId = eventId;
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
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
        Viewer viewer = (Viewer) o;
        return Objects.equals(email, viewer.email) && Objects.equals(eventId, viewer.eventId) && Objects.equals(message, viewer.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, eventId, message);
    }

    @Override
    public String toString() {
        return "Viewer{" +
                "email='" + email + '\'' +
                ", eventId=" + eventId +
                ", message='" + message + '\'' +
                '}';
    }
}
