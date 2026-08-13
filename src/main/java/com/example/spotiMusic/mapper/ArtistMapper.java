package com.example.spotiMusic.mapper;

import com.example.spotiMusic.dto.request.ArtistCreateRequest;
import com.example.spotiMusic.dto.response.ArtistResponse;
import com.example.spotiMusic.entity.ArtistEntity;
import java.time.LocalDateTime;

public class ArtistMapper {


    public static ArtistEntity toEntity(ArtistCreateRequest request) {
        if (request == null) return null;

        return ArtistEntity.builder()
                .name(request.getName())
                .country(request.getCountry())
                .biography(request.getBiography())
                .birthDate(request.getBirthDate())
                .createdDate(LocalDateTime.now())
                .build();
    }


    public static ArtistResponse toResponse(ArtistEntity entity) {
        if (entity == null) return null;

        return ArtistResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .country(entity.getCountry())
                .biography(entity.getBiography())
                .birthDate(entity.getBirthDate())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}