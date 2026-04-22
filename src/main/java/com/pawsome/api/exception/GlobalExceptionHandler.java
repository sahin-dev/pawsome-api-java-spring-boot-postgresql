package com.pawsome.api.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pawsome.api.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<?> handleJwtAuthenticationError(JwtAuthenticationException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            Map.of("error", "Authentication failed: " + ex.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidatioError(MethodArgumentNotValidException ex){
       
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
          .getFieldErrors()
          .forEach(error -> errors.put(
              error.getField(),
              error.getDefaultMessage()
          ));

        return ResponseEntity.badRequest().body(Map.of("errors", errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleEnumException(HttpMessageNotReadableException ex){
        String message = ex.getMostSpecificCause().getMessage();
         
        if(message.contains("PetTypeEnum")){
            return ResponseEntity.badRequest().body(
                Map.of("error", "Invalid petType. Allowed values are: Dog, Cat, Bird")
            );
        }
        
        return ResponseEntity.badRequest().body(
            Map.of("error", "Invalid request format")
        );
    }

    @ExceptionHandler(MyApiException.class)
    public ResponseEntity<ApiResponse<?>> handleApiException(MyApiException ex){
  
        ApiResponse<?> response = new ApiResponse<>(
            false,
            null,
            ex.getMessage()
        );

        return ResponseEntity.status(400).body(response);
    }

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<?> handleException(Exception ex){

    //     return ResponseEntity.status(400).body(ex.getCause());
    // }

}
