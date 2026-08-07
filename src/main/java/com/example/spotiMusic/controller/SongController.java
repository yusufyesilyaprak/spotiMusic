package com.example.spotiMusic.controller;

import com.example.spotiMusic.dto.request.SongCreateRequest;
import com.example.spotiMusic.dto.request.SongUpdateRequest;
import com.example.spotiMusic.dto.response.SongResponse;
import com.example.spotiMusic.service.ISongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {

    private final ISongService songService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SongResponse createSong(@Valid @RequestBody SongCreateRequest request) {
        return songService.createSong(request);
    }

    // İsme göre şarkı arama
    @GetMapping("/search")
    public ResponseEntity<List<SongResponse>> searchSongs(@RequestParam String name) {
        return ResponseEntity.ok(songService.searchSongsByName(name));
    }

    // Aktif şarkıları veya tarihe göre şarkıları getirme (Opsiyonel Parametreler)
    @GetMapping
    public ResponseEntity<List<SongResponse>> getSongs(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) LocalDate releaseDate) {

        // 4. Her iki parametre de gelirse (Artık birleşik metodu çağırıyoruz)
        if (active != null && releaseDate != null) {
            return ResponseEntity.ok(songService.getSongsByActiveStatusAndReleaseDate(active, releaseDate));
        }

        // 2. Sadece aktiflik durumuna göre filtreleme
        if (active != null) {
            return ResponseEntity.ok(songService.getSongsByActiveStatus(active));
        }

        // 3. Sadece tarihe göre filtreleme
        if (releaseDate != null) {
            return ResponseEntity.ok(songService.getSongsReleasedAfter(releaseDate));
        }

        // 1. Eğer parametreler boşsa tüm listeyi getir (En sona bıraktık)
        return ResponseEntity.ok(songService.getAllSongs());
    }

    @GetMapping("/{id}")
    public SongResponse getSongById(@PathVariable Long id) {
        return songService.getSongById(id);
    }

    @PutMapping("/{id}")
    public SongResponse updateSong(@PathVariable Long id, @Valid @RequestBody SongUpdateRequest request) {
        return songService.updateSong(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
    }
}
