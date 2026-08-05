package com.example.spotiMusic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "Kategori adı boş olamaz.")
    @Size(min = 2, message = "Kategori adı en az 2 karakter olmalıdır.")
    private String name;

    private String description;
}