package com.pawsome.api.pet.exception;

public class PetCreationFailed extends RuntimeException{

    public PetCreationFailed(String message){
        super(message);
    }

    public PetCreationFailed(String message, Throwable cause){
        super(message, cause);
    }
}
