package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.ArtistRequest;
import com.example.spotiMusic.dto.ArtistResponse;
import java.util.List;

public interface IArtistService {
    ArtistResponse createArtist(ArtistRequest request);
    List<ArtistResponse> searchArtistsByName(String name);
    ArtistResponse getArtistById(Long id);
    List<ArtistResponse> getAllArtists();
    void deleteArtist(Long id);
}