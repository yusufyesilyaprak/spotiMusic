package com.example.spotiMusic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistResponse {
    private Long id;
    private String name;
    private String country;
    private String biography;
    private LocalDate birthDate;
    private LocalDateTime createdDate;
}