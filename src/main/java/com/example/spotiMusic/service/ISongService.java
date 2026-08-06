package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.SongRequest;
import com.example.spotiMusic.dto.SongResponse;

import java.time.LocalDate;
import java.util.List;

public interface ISongService {
    // Mevcut CRUD (Temel) İşlemleri
    SongResponse createSong(SongRequest request);
    SongResponse getSongById(Long id);
    List<SongResponse> getAllSongs();
    SongResponse updateSong(Long id, SongRequest request);
    void deleteSong(Long id);

    // Yeni Eklenen Filtreleme ve Arama Metotları (STEP 6)
    List<SongResponse> searchSongsByName(String name);
    List<SongResponse> getSongsByArtistId(Long artistId);
    List<SongResponse> getSongsByCategoryId(Long categoryId);
    List<SongResponse> getSongsByActiveStatus(Boolean active);
    List<SongResponse> getSongsReleasedAfter(LocalDate date);
}