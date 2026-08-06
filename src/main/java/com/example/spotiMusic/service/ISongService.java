package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.SongRequest;
import com.example.spotiMusic.dto.SongResponse;

import java.util.List;

public interface ISongService {
    SongResponse createSong(SongRequest request);
    SongResponse getSongById(Long id);
    List<SongResponse> getAllSongs();
    SongResponse updateSong(Long id, SongRequest request);
    void deleteSong(Long id);
}