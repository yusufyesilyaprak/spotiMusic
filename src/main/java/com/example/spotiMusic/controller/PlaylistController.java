package com.example.spotiMusic.controller;

import com.example.spotiMusic.dto.request.PlaylistCreateRequest;
import com.example.spotiMusic.dto.response.PlaylistResponse;
import com.example.spotiMusic.dto.response.SongResponse;
import com.example.spotiMusic.service.iservice.IPlaylistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlaylistController {

    private final IPlaylistService playlistService;

    // 1. Create a playlist (URL updated to /playlists/create)
    @PostMapping("/playlists/create")
    public ResponseEntity<PlaylistResponse> createPlaylist(
            @Valid @RequestBody PlaylistCreateRequest request,
            Principal principal
    ) {
        return new ResponseEntity<>(playlistService.createPlaylist(request, principal.getName()), HttpStatus.CREATED);
    }

    // 2. Add a song to a playlist
    @PostMapping("/playlists/{playlistId}/songs/{songId}")
    public ResponseEntity<String> addSongToPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId,
            Principal principal
    ) {
        playlistService.addSongToPlaylist(playlistId, songId, principal.getName());
        return ResponseEntity.ok("Song added to playlist successfully.");
    }

    // 3. Remove a song from a playlist (Updated to return 204 No Content)
    @DeleteMapping("/playlists/{playlistId}/songs/{songId}")
    public ResponseEntity<Void> removeSongFromPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId,
            Principal principal
    ) {
        playlistService.removeSongFromPlaylist(playlistId, songId, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Returns 204
    }

    // 4. Get current user's playlists
    @GetMapping("/users/me/playlists")
    public ResponseEntity<List<PlaylistResponse>> getMyPlaylists(Principal principal) {
        return ResponseEntity.ok(playlistService.getUserPlaylists(principal.getName()));
    }
    // 5. Get songs of a specific playlist
    @GetMapping("/playlists/{playlistId}/songs")
    public ResponseEntity<List<SongResponse>> getPlaylistSongs(
            @PathVariable Long playlistId,
            Principal principal
    ) {
        return ResponseEntity.ok(playlistService.getPlaylistSongs(playlistId, principal.getName()));
    }
}