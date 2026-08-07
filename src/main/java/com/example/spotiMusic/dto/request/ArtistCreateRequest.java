package com.example.spotiMusic.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtistCreateRequest {

    @NotBlank(message = "Artist name cannot be blank.")
    @Size(min = 2, message = "Artist name must be at least 2 characters long.")
    private String name;

    private String country;

    private String biography;

    private LocalDate birthDate;
}