package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.CategoryRequest;
import com.example.spotiMusic.dto.CategoryResponse;
import com.example.spotiMusic.entity.CategoryEntity;
import com.example.spotiMusic.exception.CategoryAlreadyExistsException;
import com.example.spotiMusic.exception.CategoryNotFoundException;
import com.example.spotiMusic.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        CategoryEntity entity = CategoryEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdDate(LocalDateTime.now())
                .build();

        CategoryEntity savedEntity = categoryRepository.save(entity);
        return mapToResponse(savedEntity);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
        return mapToResponse(entity);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<CategoryEntity> entities = categoryRepository.findAll();
        return entities.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    private CategoryResponse mapToResponse(CategoryEntity entity) {
        return CategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName()) 
                .description(entity.getDescription())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}