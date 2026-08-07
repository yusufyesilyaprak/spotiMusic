package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.request.SongCreateRequest;
import com.example.spotiMusic.dto.request.SongUpdateRequest;
import com.example.spotiMusic.dto.response.SongResponse;

import java.time.LocalDate;
import java.util.List;

public interface ISongService {

    SongResponse createSong(SongCreateRequest request);
    SongResponse getSongById(Long id);
    List<SongResponse> getAllSongs();
    SongResponse updateSong(Long id, SongUpdateRequest request);
    void deleteSong(Long id);

    List<SongResponse> searchSongsByName(String name);
    List<SongResponse> getSongsByArtistId(Long artistId);
    List<SongResponse> getSongsByCategoryId(Long categoryId);
    List<SongResponse> getSongsByActiveStatus(Boolean active);
    List<SongResponse> getSongsReleasedAfter(LocalDate date);

    List<SongResponse> getSongsByActiveStatusAndReleaseDate(Boolean active, LocalDate date);
}