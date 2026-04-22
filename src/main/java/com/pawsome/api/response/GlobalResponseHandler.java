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
import com.pawsome.api.exception.MyApiException;


@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object>{

    @Override
    public @Nullable Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContestType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
                System.out.println(body);
        // Don't wrap error responses (Map with "errors" or "error" keys)
        if (body instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) body;
            if (map.containsKey("errors") || map.containsKey("error")) {
                return body; // Return error as-is with original status code
            }
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

    @ExceptionHandler(MyApiException.class)
    public ResponseEntity<ApiResponse<?>> handleApiException(MyApiException ex) {

        ApiResponse<?> response = new ApiResponse<>(
                false,
                null,
                ex.getMessage()
        );

        return ResponseEntity
                .status(400)
                .body(response);
    }

    
}
