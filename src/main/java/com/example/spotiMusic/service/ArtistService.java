package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.request.ArtistCreateRequest;
import com.example.spotiMusic.dto.request.ArtistUpdateRequest;
import com.example.spotiMusic.dto.response.ArtistResponse;
import com.example.spotiMusic.entity.ArtistEntity;
import com.example.spotiMusic.mapper.ArtistMapper;
import com.example.spotiMusic.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistService implements IArtistService {

    private final ArtistRepository artistRepository;

    // CREATE
    public ArtistResponse createArtist(ArtistCreateRequest request) {
        // 1. Name check
        if (artistRepository.existsByName(request.getName())) {
            throw new RuntimeException("Artist with this name already exists: " + request.getName());
        }

        //Convert Request to Entity using Mapper.
        ArtistEntity entity = ArtistMapper.toEntity(request);

        // 3. Save to database
        ArtistEntity savedEntity = artistRepository.save(entity);

        // 4. Convert to Response and return.
        return ArtistMapper.toResponse(savedEntity);
    }

    // READ (By Id)
    public ArtistResponse getArtistById(Long id) {
        ArtistEntity entity = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + id));

        return ArtistMapper.toResponse(entity);
    }

    // READ (All)
    public List<ArtistResponse> getAllArtists() {
        return artistRepository.findAll().stream()
                .map(ArtistMapper::toResponse)
                .collect(Collectors.toList());
    }

    // UPDATE
    public ArtistResponse updateArtist(Long id, ArtistUpdateRequest request) {
        // 1. Find the artist to update.
        ArtistEntity existingEntity = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + id));

        // 2. Name check: Throw an error if the name is being changed and the new name is already in use by someone else.
        if (!existingEntity.getName().equals(request.getName()) && artistRepository.existsByName(request.getName())) {
            throw new RuntimeException("Artist with this name already exists: " + request.getName());
        }

        // 3. Update fields
        existingEntity.setName(request.getName());
        existingEntity.setBiography(request.getBiography());

        // 4. Save and return Response
        ArtistEntity updatedEntity = artistRepository.save(existingEntity);
        return ArtistMapper.toResponse(updatedEntity);
    }

    // DELETE
    public void deleteArtist(Long id) {
        if (!artistRepository.existsById(id)) {
            throw new RuntimeException("Artist not found with id: " + id);
        }
        artistRepository.deleteById(id);
    }

    // SEARCH
    public List<ArtistResponse> searchArtistsByName(String name) {
        return artistRepository.findAll().stream()
                .filter(artist -> artist.getName().toLowerCase().contains(name.toLowerCase()))
                .map(ArtistMapper::toResponse)
                .collect(Collectors.toList());
    }
}