package com.example.spotiMusic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Not Found (404) Hataları
    @ExceptionHandler({
            ArtistNotFoundException.class,
            CategoryNotFoundException.class,
            SongNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNotFoundExceptions(RuntimeException ex) {
        return createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 2. Conflict (409) Hataları (Kategori veya Sanatçı isminde çakışma varsa)
    @ExceptionHandler({
            CategoryAlreadyExistsException.class,
            ArtistAlreadyExistsException.class,
            IllegalArgumentException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleConflictExceptions(RuntimeException ex) {
        return createErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    // 3. Validation (400) Hataları
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Sadece ilk validasyon hatasının mesajını alır
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return createErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    // 4. BEKLENMEYEN DİĞER TÜM HATALAR (500'ün gerçek sebebini gösterir)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleAllOtherExceptions(Exception ex) {
        ex.printStackTrace(); // Hatanın tam detayını IDE konsoluna yazdırır
        // Postman'e hatanın gerçek adını (Class Name) ve detayını döner
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected Error: " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
    }

    // Özel hata mesajı şablonu oluşturucu (Kod tekrarını önler)
    private Map<String, Object> createErrorResponse(HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }
}