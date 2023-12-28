/*
 * Copyright (c) 2023.
 * Created this for the project called "TheJackFolio"
 * All right reserved by Jack
 */

package com.esportarena.microservices.esportsarenaapi.models;

import com.esportarena.microservices.esportsarenaapi.enums.PartnerStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Partner {

    private String name;
    private String email;
    private PartnerStatus status;
    private String message;

    public Partner() {
    }

    public Partner(String name, String email, PartnerStatus status, String message) {
        this.name = name;
        this.email = email;
        this.status = status;
        this.message = message;
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

    public PartnerStatus getStatus() {
        return status;
    }

    public void setStatus(PartnerStatus status) {
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
        Partner partner = (Partner) o;
        return Objects.equals(name, partner.name) && Objects.equals(email, partner.email) && status == partner.status && Objects.equals(message, partner.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, status, message);
    }

    @Override
    public String toString() {
        return "Partner{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
