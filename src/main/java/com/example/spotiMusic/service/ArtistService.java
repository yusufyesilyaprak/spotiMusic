package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.ArtistRequest;
import com.example.spotiMusic.dto.ArtistResponse;
import com.example.spotiMusic.entity.ArtistEntity;
import com.example.spotiMusic.exception.ArtistAlreadyExistsException;
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
        // Sanatçı ismi daha önce eklenmiş mi kontrolü
        if (artistRepository.existsByName(request.getName())) {
            throw new ArtistAlreadyExistsException("An artist with this name already exists: " + request.getName());
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
    public ArtistResponse getArtistById(Long id) {
        ArtistEntity entity = artistRepository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException("Artist not found with id: " + id));
        return mapToResponse(entity);
    }

    @Override
    public List<ArtistResponse> getAllArtists() {
        return artistRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ArtistResponse updateArtist(Long id, ArtistRequest request) {
        ArtistEntity existingArtist = artistRepository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException("Artist not found with id: " + id));

        // İsim değiştiriliyorsa ve yeni isim zaten başka bir sanatçıda varsa hata fırlat
        if (!existingArtist.getName().equalsIgnoreCase(request.getName()) && artistRepository.existsByName(request.getName())) {
            throw new ArtistAlreadyExistsException("An artist with this name already exists: " + request.getName());
        }

        existingArtist.setName(request.getName());
        existingArtist.setCountry(request.getCountry());
        existingArtist.setBiography(request.getBiography());
        existingArtist.setBirthDate(request.getBirthDate());

        ArtistEntity updatedEntity = artistRepository.save(existingArtist);
        return mapToResponse(updatedEntity);
    }

    @Override
    public void deleteArtist(Long id) {
        if (!artistRepository.existsById(id)) {
            throw new ArtistNotFoundException("Artist not found with id: " + id);
        }
        artistRepository.deleteById(id);
    }
    @Override
    public List<ArtistResponse> searchArtistsByName(String name) {
        return artistRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

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