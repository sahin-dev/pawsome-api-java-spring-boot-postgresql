package com.pawsome.api.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.authorization.AuthorizationDeniedException;
import com.pawsome.api.pet.exception.FileUploadFailedException;
import com.pawsome.api.service.exceptions.ServiceNotAvailableException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(FileUploadFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> fileUploadFailedException(FileUploadFailedException ex){

        ApiError error = new ApiError();
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(400).body(error);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MethodArgumentNotValidException ex){

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return new ApiError(false, "Validation failed", errors);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleUnauthorizedAccessException(AuthorizationDeniedException ex){
        return new ApiError(false, "Unauthorized access", null);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex){
        return ResponseEntity.status(404).body(new ApiError(false, ex.getMessage(), null));
    }

    @ExceptionHandler(ServiceNotAvailableException.class)
    public ResponseEntity<?> serviceNotAvailableHanlder(ServiceNotAvailableException ex){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
        body(new ApiError(false, "Service not available", null));
    }

}
