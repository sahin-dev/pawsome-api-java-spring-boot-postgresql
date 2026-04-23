package com.pawsome.api.pet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "File upload failed!")
public class FileUploadFailedException  extends RuntimeException{

    public FileUploadFailedException(String message) {
        super(message);
       
    }

    public FileUploadFailedException(String message, Throwable cause) {
        super(message, cause);
       
    }

}
