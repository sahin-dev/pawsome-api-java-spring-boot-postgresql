package com.pawsome.api.response;

import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.pawsome.api.exception.ApiError;



@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object>{

    @Override
    public @Nullable Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContestType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
                

        if (body instanceof ApiError){
            System.err.println(body);
            return body;
        }

        // Don't wrap if already wrapped
        if (body instanceof ApiResponse) {
            return body;
        }

        // Wrap successful responses
        return new ApiResponse<>(true, body, "success");
                
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
       
        return true;
    }

    
}
