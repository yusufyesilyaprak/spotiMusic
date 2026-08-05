package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.CategoryRequest;
import com.example.spotiMusic.dto.CategoryResponse;
import com.example.spotiMusic.entity.Category;
import com.example.spotiMusic.exception.CategoryAlreadyExistsException;
import com.example.spotiMusic.exception.CategoryNotFoundException;
import com.example.spotiMusic.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new CategoryAlreadyExistsException("Bu isimde bir kategori zaten mevcut: " + request.getName());
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category savedCategory = categoryRepository.save(category);
        return mapToResponse(savedCategory);
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse getCategoryById(Long id) {
        Category category = findCategoryByIdOrThrow(id);
        return mapToResponse(category);
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = findCategoryByIdOrThrow(id);

        if (!category.getName().equals(request.getName()) && categoryRepository.existsByName(request.getName())) {
            throw new CategoryAlreadyExistsException("Bu isimde bir kategori zaten mevcut: " + request.getName());
        }

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category updatedCategory = categoryRepository.save(category);
        return mapToResponse(updatedCategory);
    }

    public void deleteCategory(Long id) {
        Category category = findCategoryByIdOrThrow(id);
        categoryRepository.delete(category);
    }

    private Category findCategoryByIdOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Kategori bulunamadı. ID: " + id));
    }

    // Builder yerine standart nesne oluşturma ile hata giderildi
    private CategoryResponse mapToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setCreatedDate(category.getCreatedDate());
        return response;
    }
}