package com.example.spotiMusic.controller;

import com.example.spotiMusic.dto.ArtistRequest;
import com.example.spotiMusic.dto.ArtistResponse;
import com.example.spotiMusic.service.ArtistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping
    public ResponseEntity<ArtistResponse> createArtist(@Valid @RequestBody ArtistRequest request) {
        return new ResponseEntity<>(artistService.createArtist(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ArtistResponse>> getAllArtists() {
        return ResponseEntity.ok(artistService.getAllArtists());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArtistResponse>> searchArtists(@RequestParam String name) {
        return ResponseEntity.ok(artistService.searchArtistsByName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> getArtistById(@PathVariable Long id) {
        return ResponseEntity.ok(artistService.getArtistById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistResponse> updateArtist(@PathVariable Long id, @Valid @RequestBody ArtistRequest request) {
        return ResponseEntity.ok(artistService.updateArtist(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}