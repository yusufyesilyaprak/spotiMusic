package com.example.spotiMusic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongResponse {

    private Long id;
    private String name;
    private Integer duration;
    private LocalDate releaseDate;
    private Boolean active;
    private SongArtistResponse artist;
    private SongCategoryResponse category;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SongArtistResponse {
        private Long id;
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SongCategoryResponse {
        private Long id;
        private String name;
    }
}