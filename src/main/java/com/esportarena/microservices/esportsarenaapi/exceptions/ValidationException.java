package com.esportarena.microservices.esportsarenaapi.exceptions;

public class ValidationException extends Exception{

    public ValidationException(String message){
        super(message);
    }
}
