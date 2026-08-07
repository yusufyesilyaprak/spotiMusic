package com.example.spotiMusic.mapper;

import com.example.spotiMusic.dto.request.CategoryCreateRequest;
import com.example.spotiMusic.dto.response.CategoryResponse;
import com.example.spotiMusic.entity.CategoryEntity;
import java.time.LocalDateTime;

public class CategoryMapper {

    // CreateRequest nesnesini veritabanı Entity'sine çevirir
    public static CategoryEntity toEntity(CategoryCreateRequest request) {
        if (request == null) return null;

        return CategoryEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdDate(LocalDateTime.now())
                .build();
    }

    // Veritabanı Entity'sini kullanıcıya dönülecek Response'a çevirir
    public static CategoryResponse toResponse(CategoryEntity entity) {
        if (entity == null) return null;

        return CategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}