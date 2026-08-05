package com.example.spotiMusic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistRequest {

    @NotBlank(message = "Artist name cannot be blank.")
    private String name;

    private String country;
    private String biography;
    private LocalDate birthDate;
}