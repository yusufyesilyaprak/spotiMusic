package com.example.spotiMusic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class SongRequest {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Duration is required")
    private Integer duration;

    private LocalDate releaseDate;

    private Boolean active;

    @NotNull(message = "Artist ID is required")
    private Long artistId;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}