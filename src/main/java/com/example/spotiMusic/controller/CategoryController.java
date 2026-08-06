package com.example.spotiMusic.controller;

import com.example.spotiMusic.dto.CategoryRequest;
import com.example.spotiMusic.dto.CategoryResponse;
import com.example.spotiMusic.dto.SongResponse;
import com.example.spotiMusic.service.ICategoryService;
import com.example.spotiMusic.service.ISongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest request) {
        return categoryService.createCategory(request);
    }
    private final ISongService songService;
    @GetMapping("/{categoryId}/songs")
    public ResponseEntity<List<SongResponse>> getSongsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(songService.getSongsByCategoryId(categoryId));
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}