package com.example.spotiMusic.service.iservice;

import com.example.spotiMusic.dto.request.PlaylistCreateRequest;
import com.example.spotiMusic.dto.response.PlaylistResponse;
import com.example.spotiMusic.dto.response.SongResponse;

import java.util.List;

public interface IPlaylistService {
    PlaylistResponse createPlaylist(PlaylistCreateRequest request, String userEmail);
    void addSongToPlaylist(Long playlistId, Long songId, String userEmail);
    void removeSongFromPlaylist(Long playlistId, Long songId, String userEmail);
    List<PlaylistResponse> getUserPlaylists(String userEmail);
    List<SongResponse> getPlaylistSongs(Long playlistId, String userEmail);
}