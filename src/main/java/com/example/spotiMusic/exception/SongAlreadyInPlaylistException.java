package com.example.spotiMusic.exception;

public class SongAlreadyInPlaylistException extends RuntimeException {
    public SongAlreadyInPlaylistException(String message) {
        super(message);
    }
}