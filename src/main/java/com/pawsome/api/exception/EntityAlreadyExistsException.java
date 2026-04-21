package com.pawsome.api.exception;

public class EntityAlreadyExistsException extends MyApiException {

    public EntityAlreadyExistsException(String message){
        super(message);
    }
}
