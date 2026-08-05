package com.example.spotiMusic.repository;

import com.example.spotiMusic.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {
    boolean existsByName(String name);
    List<ArtistEntity> findByNameContainingIgnoreCase(String name);
}