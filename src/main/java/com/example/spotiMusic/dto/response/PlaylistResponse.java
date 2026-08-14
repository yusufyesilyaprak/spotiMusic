package com.example.spotiMusic.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaylistResponse {
    private Long id;
    private String name;
    private String description;
    private Integer songCount;
}