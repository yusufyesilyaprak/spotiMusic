package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.CategoryRequest;
import com.example.spotiMusic.dto.CategoryResponse;

import java.util.List;

public interface ICategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategories();
    void deleteCategory(Long id);
}