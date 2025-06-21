package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("details", "Oops! " + ex.getMessage() + " Please try a different value.");
        response.put("message", ex.getMessage());

        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("path", request.getDescription(false).replace("uri=", ""));
        response.put("timestamp", LocalDateTime.now());


        // Human-readable message as a new field

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    // You can add more handlers for other exceptions as needed
}