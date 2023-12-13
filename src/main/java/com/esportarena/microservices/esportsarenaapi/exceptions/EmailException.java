package com.esportarena.microservices.esportsarenaapi.exceptions;

public class EmailException extends  Exception{

    public EmailException(String message, Throwable cause){
        super(message, cause);
    }
    public EmailException(String message){
        super(message);
    }
}
