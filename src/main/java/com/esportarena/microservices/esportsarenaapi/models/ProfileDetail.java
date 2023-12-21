/*
 * Copyright (c) 2023.
 * Created this for the project called "TheJackFolio"
 * All right reserved by Jack
 */

package com.esportarena.microservices.esportsarenaapi.models;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ProfileDetail {
    private String name;
    private String phone;
    private String city;
    private String email;
    private List<InterestedGame> interestedGames;
    private String message;

    public ProfileDetail() {
    }

    public ProfileDetail(String name, String phone, String city, String email, List<InterestedGame> interestedGames, String message) {
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.email = email;
        this.interestedGames = interestedGames;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<InterestedGame> getInterestedGames() {
        return interestedGames;
    }

    public void setInterestedGames(List<InterestedGame> interestedGames) {
        this.interestedGames = interestedGames;
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
        ProfileDetail detail = (ProfileDetail) o;
        return Objects.equals(name, detail.name) && Objects.equals(phone, detail.phone) && Objects.equals(city, detail.city) && Objects.equals(email, detail.email) && Objects.equals(interestedGames, detail.interestedGames) && Objects.equals(message, detail.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, city, email, interestedGames, message);
    }

    @Override
    public String toString() {
        return "ProfileDetail{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", interestedGames=" + interestedGames +
                ", message='" + message + '\'' +
                '}';
    }
}
