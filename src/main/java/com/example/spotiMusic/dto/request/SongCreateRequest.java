package com.example.spotiMusic.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongCreateRequest {

    @NotBlank(message = "Song name cannot be blank.")
    private String name;

    private Integer duration;
    private LocalDate releaseDate;
    private Boolean active;

    @NotNull(message = "Artist ID cannot be null.")
    private Long artistId;

    @NotNull(message = "Category ID cannot be null.")
    private Long categoryId;
}