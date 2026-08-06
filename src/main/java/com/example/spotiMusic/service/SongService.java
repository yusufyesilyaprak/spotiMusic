package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.SongRequest;
import com.example.spotiMusic.dto.SongResponse;
import com.example.spotiMusic.entity.ArtistEntity;
import com.example.spotiMusic.entity.CategoryEntity;
import com.example.spotiMusic.entity.SongEntity;
import com.example.spotiMusic.exception.ArtistNotFoundException;
import com.example.spotiMusic.exception.CategoryNotFoundException;
import com.example.spotiMusic.exception.SongNotFoundException;
import com.example.spotiMusic.repository.ArtistRepository;
import com.example.spotiMusic.repository.CategoryRepository;
import com.example.spotiMusic.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongService implements ISongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public SongResponse createSong(SongRequest request) {
        ArtistEntity artist = artistRepository.findById(request.getArtistId())
                .orElseThrow(() -> new ArtistNotFoundException("Artist not found with id: " + request.getArtistId()));

        CategoryEntity category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + request.getCategoryId()));

        SongEntity entity = SongEntity.builder()
                .name(request.getName())
                .duration(request.getDuration())
                .releaseDate(request.getReleaseDate())
                .active(request.getActive() != null ? request.getActive() : true)
                .artist(artist)
                .category(category)
                .createdDate(LocalDateTime.now())
                .build();

        SongEntity savedEntity = songRepository.save(entity);
        return mapToResponse(savedEntity);
    }

    @Override
    public SongResponse getSongById(Long id) {
        SongEntity entity = songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException("Song not found with id: " + id));
        return mapToResponse(entity);
    }

    // İsme göre arama
    public List<SongResponse> searchSongsByName(String name) {
        // BURASI DÜZELTİLDİ: findByTitle yerine findByName kullanıldı.
        return songRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Sanatçıya göre arama
    public List<SongResponse> getSongsByArtistId(Long artistId) {
        return songRepository.findByArtistId(artistId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Kategoriye göre arama
    public List<SongResponse> getSongsByCategoryId(Long categoryId) {
        return songRepository.findByCategoryId(categoryId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Aktiflik durumuna göre getirme
    public List<SongResponse> getSongsByActiveStatus(Boolean active) {
        return songRepository.findByActive(active).stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Tarihe göre filtreleme
    public List<SongResponse> getSongsReleasedAfter(LocalDate date) {
        return songRepository.findByReleaseDateAfter(date).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<SongResponse> getAllSongs() {
        return songRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SongResponse updateSong(Long id, SongRequest request) {
        SongEntity existingSong = songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException("Song not found with id: " + id));

        ArtistEntity artist = artistRepository.findById(request.getArtistId())
                .orElseThrow(() -> new ArtistNotFoundException("Artist not found with id: " + request.getArtistId()));

        CategoryEntity category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + request.getCategoryId()));

        existingSong.setName(request.getName());
        existingSong.setDuration(request.getDuration());
        existingSong.setReleaseDate(request.getReleaseDate());
        existingSong.setActive(request.getActive());
        existingSong.setArtist(artist);
        existingSong.setCategory(category);

        SongEntity updatedEntity = songRepository.save(existingSong);
        return mapToResponse(updatedEntity);
    }

    @Override
    public void deleteSong(Long id) {
        if (!songRepository.existsById(id)) {
            throw new SongNotFoundException("Song not found with id: " + id);
        }
        songRepository.deleteById(id);
    }

    private SongResponse mapToResponse(SongEntity entity) {
        return SongResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .duration(entity.getDuration())
                .releaseDate(entity.getReleaseDate())
                .active(entity.getActive())
                .artistId(entity.getArtist().getId())
                .artistName(entity.getArtist().getName())
                .categoryId(entity.getCategory().getId())
                .categoryName(entity.getCategory().getName())
                .build();
    }
}