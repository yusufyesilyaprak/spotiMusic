package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.request.ArtistCreateRequest;
import com.example.spotiMusic.dto.request.ArtistUpdateRequest;
import com.example.spotiMusic.dto.response.ArtistResponse;
import java.util.List;

public interface IArtistService {

    ArtistResponse createArtist(ArtistCreateRequest request);

    ArtistResponse getArtistById(Long id);

    List<ArtistResponse> getAllArtists();

    ArtistResponse updateArtist(Long id, ArtistUpdateRequest request);

    void deleteArtist(Long id);

     List<ArtistResponse> searchArtistsByName(String name);
}