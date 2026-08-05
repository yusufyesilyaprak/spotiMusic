package com.example.spotiMusic.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistResponse {

    private Long id;
    private String name;
    private String country;
    private String biography;
    private LocalDate birthDate;
    private LocalDateTime createdDate;
}