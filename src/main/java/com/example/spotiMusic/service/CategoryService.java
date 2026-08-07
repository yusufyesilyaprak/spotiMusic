package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.request.CategoryCreateRequest;
import com.example.spotiMusic.dto.request.CategoryUpdateRequest;
import com.example.spotiMusic.dto.response.CategoryResponse;
import com.example.spotiMusic.entity.CategoryEntity;
import com.example.spotiMusic.mapper.CategoryMapper;
import com.example.spotiMusic.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        // İsim kontrolü: Aynı isimde kategori var mı?
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Category with this name already exists: " + request.getName());
        }

        CategoryEntity entity = CategoryMapper.toEntity(request);
        CategoryEntity savedEntity = categoryRepository.save(entity);
        return CategoryMapper.toResponse(savedEntity);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return CategoryMapper.toResponse(entity);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest request) {
        CategoryEntity existingEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        // İsim kontrolü: Eğer isim değiştiriliyorsa ve yeni isim başkası tarafından kullanılıyorsa hata fırlat
        if (!existingEntity.getName().equals(request.getName()) && categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Category with this name already exists: " + request.getName());
        }

        existingEntity.setName(request.getName());
        existingEntity.setDescription(request.getDescription());

        CategoryEntity updatedEntity = categoryRepository.save(existingEntity);
        return CategoryMapper.toResponse(updatedEntity);
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}