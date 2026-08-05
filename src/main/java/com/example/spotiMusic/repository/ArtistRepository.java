package com.example.spotiMusic.repository;

import com.example.spotiMusic.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    boolean existsByName(String name);
    List<Artist> findByNameContainingIgnoreCase(String name);
}