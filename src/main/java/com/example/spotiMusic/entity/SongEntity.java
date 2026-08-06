package com.example.spotiMusic.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "songs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer duration;

    private LocalDate releaseDate;

    @Column(nullable = false)
    private Boolean active = true; // Varsayılan olarak aktif başlasın

    // Bir şarkının bir sanatçısı vardır. (Many Songs -> One Artist)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private ArtistEntity artist;

    // Bir şarkının bir kategorisi vardır. (Many Songs -> One Category)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    private LocalDateTime createdDate;
}