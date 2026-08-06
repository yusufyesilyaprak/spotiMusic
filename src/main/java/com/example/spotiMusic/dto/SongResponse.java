package com.example.spotiMusic.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongResponse {
    private Long id;
    private String name;
    private Integer duration;
    private LocalDate releaseDate;
    private Boolean active;
    private Long artistId;
    private String artistName;
    private Long categoryId;
    private String categoryName;
}