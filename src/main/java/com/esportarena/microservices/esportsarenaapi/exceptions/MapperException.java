/*
 * Copyright (c) 2023.
 * Created this for the project called "TheJackFolio"
 * All right reserved by Jack
 */

package com.esportarena.microservices.esportsarenaapi.exceptions;

public class MapperException extends Exception {

    public MapperException(String message){
        super(message);
    }
    public MapperException(String message, Throwable cause) {
        super(message, cause);
    }
}
