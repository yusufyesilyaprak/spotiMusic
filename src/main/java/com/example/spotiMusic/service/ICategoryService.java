package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.request.CategoryCreateRequest;
import com.example.spotiMusic.dto.request.CategoryUpdateRequest; // Update request importu eklendi
import com.example.spotiMusic.dto.response.CategoryResponse;

import java.util.List;

public interface ICategoryService {
    CategoryResponse createCategory(CategoryCreateRequest request);
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategories();
    CategoryResponse updateCategory(Long id, CategoryUpdateRequest request);

    void deleteCategory(Long id);
}