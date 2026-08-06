package com.example.spotiMusic.controller;

import com.example.spotiMusic.dto.SongRequest;
import com.example.spotiMusic.dto.SongResponse;
import com.example.spotiMusic.service.ISongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {

    private final ISongService songService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SongResponse createSong(@Valid @RequestBody SongRequest request) {
        return songService.createSong(request);
    }

    @GetMapping
    public List<SongResponse> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("/{id}")
    public SongResponse getSongById(@PathVariable Long id) {
        return songService.getSongById(id);
    }

    @PutMapping("/{id}")
    public SongResponse updateSong(@PathVariable Long id, @Valid @RequestBody SongRequest request) {
        return songService.updateSong(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
    }
}