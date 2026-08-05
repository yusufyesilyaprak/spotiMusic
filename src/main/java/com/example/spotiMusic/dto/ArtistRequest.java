package com.example.spotiMusic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistRequest {

    @NotBlank(message = "Sanatçı adı boş olamaz")
    private String name;

    private String country;
    private String biography;
    private LocalDate birthDate;
}