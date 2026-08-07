package com.example.spotiMusic.repository;

import com.example.spotiMusic.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {
    List<ArtistEntity> findByNameContainingIgnoreCase(String name);

    boolean existsByName(String name);
}
