package com.pawsome.api.service.exceptions;

public class ServiceNotAvailableException extends Exception{

    public ServiceNotAvailableException(String message){
        super(message);

    }

    public ServiceNotAvailableException(String message, Throwable  cause){
        super(message, cause);
    }

}
