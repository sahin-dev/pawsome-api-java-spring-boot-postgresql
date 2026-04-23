package com.pawsome.api.auth.exception;

public class EntityAlreadyExistsException extends Exception{

     public EntityAlreadyExistsException(String messsage){
        super(messsage);    
    }

    public EntityAlreadyExistsException(String message, Throwable cause){
        super(message, cause);
    }
    
}
