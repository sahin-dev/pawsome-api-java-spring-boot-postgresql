package com.pawsome.api.exception;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private boolean success = false;
    private String message;
    private Map<String, String>  errors; 


}
