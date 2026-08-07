package com.example.spotiMusic.mapper;

import com.example.spotiMusic.dto.request.SongCreateRequest;
import com.example.spotiMusic.dto.response.SongResponse;
import com.example.spotiMusic.entity.ArtistEntity;
import com.example.spotiMusic.entity.CategoryEntity;
import com.example.spotiMusic.entity.SongEntity;
import java.time.LocalDateTime;

public class SongMapper {

    // Request'ten gelen verilerle birlikte, Service katmanında DB'den çektiğimiz ilişkili nesneleri de alıyoruz
    public static SongEntity toEntity(SongCreateRequest request, ArtistEntity artist, CategoryEntity category) {
        if (request == null) return null;

        return SongEntity.builder()
                .name(request.getName())
                .duration(request.getDuration())
                .releaseDate(request.getReleaseDate())
                .active(request.getActive() != null ? request.getActive() : true) // Null ise varsayılan true
                .artist(artist)     // Dışarıdan parametre olarak gelen Entity
                .category(category) // Dışarıdan parametre olarak gelen Entity
                .createdDate(LocalDateTime.now())
                .build();
    }

    public static SongResponse toResponse(SongEntity entity) {
        if (entity == null) return null;

        return SongResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .duration(entity.getDuration())
                .releaseDate(entity.getReleaseDate())
                .active(entity.getActive())

                // Artist dönüşümü
                .artist(entity.getArtist() != null ?
                        SongResponse.SongArtistResponse.builder()
                                .id(entity.getArtist().getId())
                                .name(entity.getArtist().getName())
                                .build() : null)

                // Category dönüşümü
                .category(entity.getCategory() != null ?
                        SongResponse.SongCategoryResponse.builder()
                                .id(entity.getCategory().getId())
                                .name(entity.getCategory().getName())
                                .build() : null)
                .build();
    }
}