package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.ArtistRequest;
import com.example.spotiMusic.dto.ArtistResponse;
import com.example.spotiMusic.entity.ArtistEntity;
import com.example.spotiMusic.exception.ArtistNotFoundException;
import com.example.spotiMusic.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistService implements IArtistService {

    private final ArtistRepository artistRepository;

    @Override
    public ArtistResponse createArtist(ArtistRequest request) {
        if (artistRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("An artist with this name already exists.");
        }

        ArtistEntity entity = ArtistEntity.builder()
                .name(request.getName())
                .country(request.getCountry())
                .biography(request.getBiography())
                .birthDate(request.getBirthDate())
                .createdDate(LocalDateTime.now())
                .build();

        ArtistEntity savedEntity = artistRepository.save(entity);
        return mapToResponse(savedEntity);
    }

    @Override
    public List<ArtistResponse> searchArtistsByName(String name) {
        List<ArtistEntity> entities = artistRepository.findByNameContainingIgnoreCase(name);
        return entities.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ArtistResponse getArtistById(Long id) {
        ArtistEntity entity = artistRepository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException("Artist not found with id: " + id));
        return mapToResponse(entity);
    }

    @Override
    public List<ArtistResponse> getAllArtists() {
        List<ArtistEntity> entities = artistRepository.findAll();
        return entities.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteArtist(Long id) {
        if (!artistRepository.existsById(id)) {
            throw new ArtistNotFoundException("Artist not found with id: " + id);
        }
        artistRepository.deleteById(id);
    }

    // Entity'den Response DTO'ya dönüştürme işlemini yapan yardımcı metot
    private ArtistResponse mapToResponse(ArtistEntity entity) {
        return ArtistResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .country(entity.getCountry())
                .biography(entity.getBiography())
                .birthDate(entity.getBirthDate())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}