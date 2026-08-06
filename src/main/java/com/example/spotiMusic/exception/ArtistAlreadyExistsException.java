package com.example.spotiMusic.exception;

public class ArtistAlreadyExistsException extends RuntimeException {
    public ArtistAlreadyExistsException(String message) {
        super(message);
    }
}