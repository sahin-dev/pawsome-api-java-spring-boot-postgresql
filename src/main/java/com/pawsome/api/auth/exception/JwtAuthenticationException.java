package com.pawsome.api.auth.exception;

public class JwtAuthenticationException extends Exception{

    public JwtAuthenticationException(String messsage){
        super(messsage);    
    }

    public JwtAuthenticationException(String message, Throwable cause){
        super(message, cause);
    }
}
