package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.request.PlaylistCreateRequest;
import com.example.spotiMusic.dto.response.PlaylistResponse;
import com.example.spotiMusic.dto.response.SongResponse;
import com.example.spotiMusic.entity.PlaylistEntity;
import com.example.spotiMusic.entity.SongEntity;
import com.example.spotiMusic.entity.UserEntity;
import com.example.spotiMusic.exception.PlaylistNotFoundException;
import com.example.spotiMusic.exception.SongAlreadyInPlaylistException;
import com.example.spotiMusic.exception.SongNotFoundException;
import com.example.spotiMusic.exception.SongNotInPlaylistException;
import com.example.spotiMusic.repository.PlaylistRepository;
import com.example.spotiMusic.repository.SongRepository;
import com.example.spotiMusic.repository.UserRepository;
import com.example.spotiMusic.service.iservice.IPlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistService implements IPlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    @Override
    public PlaylistResponse createPlaylist(PlaylistCreateRequest request, String userEmail) {
        UserEntity user = getUserByEmail(userEmail);

        PlaylistEntity playlist = PlaylistEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .user(user)
                .build();

        PlaylistEntity savedPlaylist = playlistRepository.save(playlist);

        return mapToResponse(savedPlaylist);
    }

    @Override
    public void addSongToPlaylist(Long playlistId, Long songId, String userEmail) {
        PlaylistEntity playlist = getPlaylistAndVerifyOwnership(playlistId, userEmail);
        SongEntity song = songRepository.findById(songId)
                .orElseThrow(() -> new SongNotFoundException("Song not found with id: " + songId));

        // Validation: Is the song already in the playlist? (Throws 409)
        if (playlist.getSongs().contains(song)) {
            throw new SongAlreadyInPlaylistException("Song is already in the playlist.");
        }

        playlist.getSongs().add(song);
        playlistRepository.save(playlist);
    }

    @Override
    public void removeSongFromPlaylist(Long playlistId, Long songId, String userEmail) {
        PlaylistEntity playlist = getPlaylistAndVerifyOwnership(playlistId, userEmail);
        SongEntity song = songRepository.findById(songId)
                .orElseThrow(() -> new SongNotFoundException("Song not found with id: " + songId));

        // Validation: Is the song actually in the playlist before removing? (Throws 404)
        if (!playlist.getSongs().contains(song)) {
            throw new SongNotInPlaylistException("Song is not present in this playlist.");
        }

        playlist.getSongs().remove(song);
        playlistRepository.save(playlist);
    }

    @Override
    public List<PlaylistResponse> getUserPlaylists(String userEmail) {
        UserEntity user = getUserByEmail(userEmail);

        return playlistRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SongResponse> getPlaylistSongs(Long playlistId, String userEmail) {
        // 1. Get playlist and verify if it belongs to the logged-in user
        PlaylistEntity playlist = getPlaylistAndVerifyOwnership(playlistId, userEmail);

        // 2. Map SongEntity list to SongResponse list
        return playlist.getSongs().stream()
                .map(song -> SongResponse.builder()
                        .id(song.getId())
                        .name(song.getName())
                        .duration(song.getDuration())
                        .build())
                .collect(Collectors.toList());
    }


    private UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    private PlaylistEntity getPlaylistAndVerifyOwnership(Long playlistId, String userEmail) {
        PlaylistEntity playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found with id: " + playlistId));

        // Verify if the logged-in user is the owner of the playlist
        if (!playlist.getUser().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("You do not have permission to modify this playlist.");
        }
        return playlist;
    }

    private PlaylistResponse mapToResponse(PlaylistEntity playlist) {

        int count = (playlist.getSongs() != null) ? playlist.getSongs().size() : 0;

        return PlaylistResponse.builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .description(playlist.getDescription())
                .songCount(count)
                .build();
    }
}