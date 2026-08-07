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
    public ResponseEntity<SongResponse> createSong(@Valid @RequestBody SongCreateRequest request) {
        return new ResponseEntity<>(songService.createSong(request), HttpStatus.CREATED);
    }

    // Search songs by name
    @GetMapping("/search")
    public ResponseEntity<List<SongResponse>> searchSongs(@RequestParam String name) {
        return ResponseEntity.ok(songService.searchSongsByName(name));
    }

    // --- NEWLY ADDED: Get songs by artist ID ---
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<SongResponse>> getSongsByArtistId(@PathVariable Long artistId) {
        return ResponseEntity.ok(songService.getSongsByArtistId(artistId));
    }

    // --- NEWLY ADDED: Get songs by category ID ---
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<SongResponse>> getSongsByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(songService.getSongsByCategoryId(categoryId));
    }

    // Get active songs or songs by date (Optional Parameters)
    @GetMapping
    public ResponseEntity<List<SongResponse>> getSongs(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) LocalDate releaseDate) {

        // 4. If both parameters are provided (Now calling the combined method)
        if (active != null && releaseDate != null) {
            return ResponseEntity.ok(songService.getSongsByActiveStatusAndReleaseDate(active, releaseDate));
        }

        // 2. Filtering only by active status
        if (active != null) {
            return ResponseEntity.ok(songService.getSongsByActiveStatus(active));
        }

        // 3. Filtering only by release date
        if (releaseDate != null) {
            return ResponseEntity.ok(songService.getSongsReleasedAfter(releaseDate));
        }

        // 1. If parameters are empty, return the full list (Left as fallback)
        return ResponseEntity.ok(songService.getAllSongs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongResponse> getSongById(@PathVariable Long id) {
        return ResponseEntity.ok(songService.getSongById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongResponse> updateSong(
            @PathVariable Long id,
            @Valid @RequestBody SongUpdateRequest request) {
        return ResponseEntity.ok(songService.updateSong(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }
}