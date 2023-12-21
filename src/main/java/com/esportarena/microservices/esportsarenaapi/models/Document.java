/*
 * Copyright (c) 2023.
 * Created this for the project called "TheJackFolio"
 * All right reserved by Jack
 */

package com.esportarena.microservices.esportsarenaapi.models;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class Document {

    private byte[] document;
    private String message;

    public Document() {
    }

    public Document(byte[] document, String message) {
        this.document = document;
        this.message = message;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
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
        Document document1 = (Document) o;
        return Arrays.equals(document, document1.document) && Objects.equals(message, document1.message);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(message);
        result = 31 * result + Arrays.hashCode(document);
        return result;
    }

    @Override
    public String toString() {
        return "Document{" +
                "document=" + Arrays.toString(document) +
                ", message='" + message + '\'' +
                '}';
    }
}
