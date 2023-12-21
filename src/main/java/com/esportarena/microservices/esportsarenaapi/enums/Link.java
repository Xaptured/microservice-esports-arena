/*
 * Copyright (c) 2023.
 * Created this for the project called "TheJackFolio"
 * All right reserved by Jack
 */

package com.esportarena.microservices.esportsarenaapi.enums;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

@Component
public class Link implements Serializable {

    private String linkedinLink;
    private String githubLink;
    private String youtubeLink;
    private String instagramLink;

    public Link() {
    }

    public Link(String linkedinLink, String githubLink, String youtubeLink, String instagramLink) {
        this.linkedinLink = linkedinLink;
        this.githubLink = githubLink;
        this.youtubeLink = youtubeLink;
        this.instagramLink = instagramLink;
    }

    public String getLinkedinLink() {
        return linkedinLink;
    }

    public void setLinkedinLink(String linkedinLink) {
        this.linkedinLink = linkedinLink;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(linkedinLink, link.linkedinLink) && Objects.equals(githubLink, link.githubLink) && Objects.equals(youtubeLink, link.youtubeLink) && Objects.equals(instagramLink, link.instagramLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(linkedinLink, githubLink, youtubeLink, instagramLink);
    }

    @Override
    public String toString() {
        return "Link{" +
                "linkedinLink='" + linkedinLink + '\'' +
                ", githubLink='" + githubLink + '\'' +
                ", youtubeLink='" + youtubeLink + '\'' +
                ", instagramLink='" + instagramLink + '\'' +
                '}';
    }
}
