/*
 * Copyright (c) 2023.
 * Created this for the project called "TheJackFolio"
 * All right reserved by Jack
 */

package com.esportarena.microservices.esportsarenaapi.models;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Rule {

    private Integer ruleNumber;
    private String description;

    public Rule() {
    }

    public Rule(Integer ruleNumber, String description) {
        this.ruleNumber = ruleNumber;
        this.description = description;
    }

    public Integer getRuleNumber() {
        return ruleNumber;
    }

    public void setRuleNumber(Integer ruleNumber) {
        this.ruleNumber = ruleNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(ruleNumber, rule.ruleNumber) && Objects.equals(description, rule.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleNumber, description);
    }

    @Override
    public String toString() {
        return "Rule{" +
                "ruleNumber=" + ruleNumber +
                ", description='" + description + '\'' +
                '}';
    }
}
