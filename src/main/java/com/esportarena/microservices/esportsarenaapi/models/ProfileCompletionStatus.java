package com.esportarena.microservices.esportsarenaapi.models;

import java.util.Objects;

public class ProfileCompletionStatus {

    private boolean isProfileComplete;
    private String message;

    public ProfileCompletionStatus() {
    }

    public ProfileCompletionStatus(boolean isProfileComplete, String message) {
        this.isProfileComplete = isProfileComplete;
        this.message = message;
    }

    public boolean isProfileComplete() {
        return isProfileComplete;
    }

    public void setProfileComplete(boolean profileComplete) {
        isProfileComplete = profileComplete;
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
        ProfileCompletionStatus that = (ProfileCompletionStatus) o;
        return isProfileComplete == that.isProfileComplete && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isProfileComplete, message);
    }

    @Override
    public String toString() {
        return "ProfileCompletionStatus{" +
                "isProfileComplete=" + isProfileComplete +
                ", message='" + message + '\'' +
                '}';
    }
}
