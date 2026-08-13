package com.example.spotiMusic.repository;

import com.example.spotiMusic.entity.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long> {

    // Find all playlists belonging to a specific user ID
    List<PlaylistEntity> findByUserId(Long userId);
}