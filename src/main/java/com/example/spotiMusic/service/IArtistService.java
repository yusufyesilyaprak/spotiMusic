package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.ArtistRequest;
import com.example.spotiMusic.dto.ArtistResponse;
import java.util.List;

public interface IArtistService {

    ArtistResponse createArtist(ArtistRequest request);

    ArtistResponse getArtistById(Long id);

    List<ArtistResponse> getAllArtists();

    ArtistResponse updateArtist(Long id, ArtistRequest request);

    void deleteArtist(Long id);

     List<ArtistResponse> searchArtistsByName(String name);
}