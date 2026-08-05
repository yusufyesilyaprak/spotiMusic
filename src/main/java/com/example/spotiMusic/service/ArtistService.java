package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.ArtistRequest;
import com.example.spotiMusic.dto.ArtistResponse;
import com.example.spotiMusic.entity.Artist;
import com.example.spotiMusic.exception.ArtistNotFoundException;
import com.example.spotiMusic.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistResponse createArtist(ArtistRequest request) {
        if (artistRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Bu isimde bir sanatçı zaten mevcut.");
        }

        Artist artist = new Artist();
        artist.setName(request.getName());
        artist.setCountry(request.getCountry());
        artist.setBiography(request.getBiography());
        artist.setBirthDate(request.getBirthDate());

        Artist savedArtist = artistRepository.save(artist);
        return mapToResponse(savedArtist);
    }

    public List<ArtistResponse> getAllArtists() {
        return artistRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ArtistResponse getArtistById(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException("Sanatçı bulunamadı ID: " + id));
        return mapToResponse(artist);
    }

    public ArtistResponse updateArtist(Long id, ArtistRequest request) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException("Sanatçı bulunamadı ID: " + id));

        artist.setName(request.getName());
        artist.setCountry(request.getCountry());
        artist.setBiography(request.getBiography());
        artist.setBirthDate(request.getBirthDate());

        Artist updatedArtist = artistRepository.save(artist);
        return mapToResponse(updatedArtist);
    }

    public void deleteArtist(Long id) {
        if (!artistRepository.existsById(id)) {
            throw new ArtistNotFoundException("Sanatçı bulunamadı ID: " + id);
        }
        artistRepository.deleteById(id);
    }

    public List<ArtistResponse> searchArtistsByName(String name) {
        return artistRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ArtistResponse mapToResponse(Artist artist) {
        return new ArtistResponse(
                artist.getId(),
                artist.getName(),
                artist.getCountry(),
                artist.getBiography(),
                artist.getBirthDate(),
                artist.getCreatedDate()
        );
    }
}