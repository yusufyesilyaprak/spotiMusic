package com.example.spotiMusic.repository;

import com.example.spotiMusic.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long> {

    List<SongEntity> findByNameContainingIgnoreCase(String name);

    List<SongEntity> findByArtistId(Long artistId);

    List<SongEntity> findByCategoryId(Long categoryId);

    List<SongEntity> findByActive(Boolean active);

    List<SongEntity> findByReleaseDateAfter(LocalDate releaseDate);

    List<SongEntity> findByActiveAndReleaseDateAfter(Boolean active, LocalDate releaseDate);
}