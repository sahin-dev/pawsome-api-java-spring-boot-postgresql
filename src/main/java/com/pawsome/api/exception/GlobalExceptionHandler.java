package com.pawsome.api.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pawsome.api.pet.exception.FileUploadFailedException;
import com.pawsome.api.response.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(FileUploadFailedException.class)
    public ResponseEntity<?> fileUploadFailedException(FileUploadFailedException ex, HttpServletRequest request){

        ApiError error = new ApiError();
        error.setMessage(ex.getMessage());
        error.setStatusCode(400);
        error.setUrl(request.getRequestURI());
        return ResponseEntity.status(400).body(error);
    }
    

}
