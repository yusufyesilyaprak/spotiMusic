package com.example.spotiMusic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongRequest {

    @NotBlank(message = "Song name cannot be blank.")
    private String name;

    @NotNull(message = "Duration cannot be null.")
    @Positive(message = "Duration must be greater than zero.")
    private Integer duration;

    private LocalDate releaseDate;

    @NotNull(message = "Artist ID cannot be null.")
    private Long artistId;

    @NotNull(message = "Category ID cannot be null.")
    private Long categoryId;

    private Boolean active;
}