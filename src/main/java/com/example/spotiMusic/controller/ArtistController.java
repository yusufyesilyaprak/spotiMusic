package com.example.spotiMusic.controller;

import com.example.spotiMusic.dto.ArtistRequest;
import com.example.spotiMusic.dto.ArtistResponse;
import com.example.spotiMusic.service.IArtistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final IArtistService artistService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistResponse createArtist(@Valid @RequestBody ArtistRequest request) {
        return artistService.createArtist(request);
    }

    @GetMapping("/search")
    public List<ArtistResponse> searchArtistsByName(@RequestParam String name) {
        return artistService.searchArtistsByName(name);
    }

    @GetMapping("/{id}")
    public ArtistResponse getArtistById(@PathVariable Long id) {
        return artistService.getArtistById(id);
    }

    @GetMapping
    public List<ArtistResponse> getAllArtists() {
        return artistService.getAllArtists();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
    }
}