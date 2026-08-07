package com.example.spotiMusic.mapper;

import com.example.spotiMusic.dto.request.CategoryCreateRequest;
import com.example.spotiMusic.dto.response.CategoryResponse;
import com.example.spotiMusic.entity.CategoryEntity;
import java.time.LocalDateTime;

public class CategoryMapper {

    // Converts the CreateRequest object into a database Entity.
    public static CategoryEntity toEntity(CategoryCreateRequest request) {
        if (request == null) return null;

        return CategoryEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdDate(LocalDateTime.now())
                .build();
    }

    // Converts the database Entity into the Response to be returned to the user.
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