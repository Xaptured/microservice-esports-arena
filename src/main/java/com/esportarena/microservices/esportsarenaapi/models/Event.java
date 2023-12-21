/*
 * Copyright (c) 2023.
 * Created this for the project called "TheJackFolio"
 * All right reserved by Jack
 */

package com.esportarena.microservices.esportsarenaapi.models;

import com.esportarena.microservices.esportsarenaapi.enums.EventStatus;
import com.esportarena.microservices.esportsarenaapi.enums.EventType;

import java.util.List;
import java.util.Objects;

public class Event {

    private String name;
    private String email;
    private String gameName;
    private EventStatus status;
    private String date;
    private String time;
    private String duration;
    private Integer playersPerSlot;
    private Integer slotCount;
    private Integer remainingSlots;
    private EventType type;
    private Float prizePool;
    private List<Rule> rules;
    private String message;

    public Event() {
    }

    public Event(String name, String email, EventStatus status, String date, String time, String duration, Integer playersPerSlot, Integer slotCount, Integer remainingSlots, EventType type, Float prizePool, List<Rule> rules, String message, String gameName) {
        this.name = name;
        this.email = email;
        this.status = status;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.playersPerSlot = playersPerSlot;
        this.slotCount = slotCount;
        this.remainingSlots = remainingSlots;
        this.type = type;
        this.prizePool = prizePool;
        this.rules = rules;
        this.message = message;
        this.gameName = gameName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getPlayersPerSlot() {
        return playersPerSlot;
    }

    public void setPlayersPerSlot(Integer playersPerSlot) {
        this.playersPerSlot = playersPerSlot;
    }

    public Integer getSlotCount() {
        return slotCount;
    }

    public void setSlotCount(Integer slotCount) {
        this.slotCount = slotCount;
    }

    public Integer getRemainingSlots() {
        return remainingSlots;
    }

    public void setRemainingSlots(Integer remainingSlots) {
        this.remainingSlots = remainingSlots;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Float getPrizePool() {
        return prizePool;
    }

    public void setPrizePool(Float prizePool) {
        this.prizePool = prizePool;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(name, event.name) && Objects.equals(email, event.email) && Objects.equals(gameName, event.gameName) && status == event.status && Objects.equals(date, event.date) && Objects.equals(time, event.time) && Objects.equals(duration, event.duration) && Objects.equals(playersPerSlot, event.playersPerSlot) && Objects.equals(slotCount, event.slotCount) && Objects.equals(remainingSlots, event.remainingSlots) && type == event.type && Objects.equals(prizePool, event.prizePool) && Objects.equals(rules, event.rules) && Objects.equals(message, event.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, gameName, status, date, time, duration, playersPerSlot, slotCount, remainingSlots, type, prizePool, rules, message);
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", gameName='" + gameName + '\'' +
                ", status=" + status +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", duration='" + duration + '\'' +
                ", playersPerSlot=" + playersPerSlot +
                ", slotCount=" + slotCount +
                ", remainingSlots=" + remainingSlots +
                ", type=" + type +
                ", prizePool=" + prizePool +
                ", rules=" + rules +
                ", message='" + message + '\'' +
                '}';
    }
}
