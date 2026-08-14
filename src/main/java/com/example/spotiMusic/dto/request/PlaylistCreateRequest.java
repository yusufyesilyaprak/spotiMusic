package com.example.spotiMusic.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PlaylistCreateRequest {

    @NotBlank(message = "Playlist name cannot be blank.")
    private String name;

    private String description;
}