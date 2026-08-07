package com.example.spotiMusic.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Not Found (404) Exceptions
    @ExceptionHandler({
            ArtistNotFoundException.class,
            CategoryNotFoundException.class,
            SongNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNotFoundExceptions(RuntimeException ex, HttpServletRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    // 2. Conflict (409) Exceptions (e.g., category or artist name conflicts)
    @ExceptionHandler({
            CategoryAlreadyExistsException.class,
            ArtistAlreadyExistsException.class,
            IllegalArgumentException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleConflictExceptions(RuntimeException ex, HttpServletRequest request) {
        return createErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI());
    }

    // 3. Validation (400) Exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Validation error");

        // Extract all errors as field name and message
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            validationErrors.put(error.getField(), error.getDefaultMessage());
        }

        response.put("errors", validationErrors);
        return response;
    }

    // 4. ALL OTHER UNEXPECTED ERRORS
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleAllOtherExceptions(Exception ex, HttpServletRequest request) {
        ex.printStackTrace(); // For IDE console
        return createErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected Error: " + ex.getClass().getSimpleName() + " - " + ex.getMessage(),
                request.getRequestURI()
        );
    }

    // Custom error message template builder - 'path' parameter added
    private Map<String, Object> createErrorResponse(HttpStatus status, String message, String path) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);
        response.put("path", path);
        return response;
    }
}