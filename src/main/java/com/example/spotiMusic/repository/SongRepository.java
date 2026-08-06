package com.example.spotiMusic.repository;

import com.example.spotiMusic.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<SongEntity, Long> {
}