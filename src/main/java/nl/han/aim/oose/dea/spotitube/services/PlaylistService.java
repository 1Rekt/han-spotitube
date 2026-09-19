package nl.han.aim.oose.dea.spotitube.services;

import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistsDTO;

public interface PlaylistService {
    PlaylistsDTO getAllPlaylists(String token);

    void editPlaylist(PlaylistDTO playlist);

    void addPlaylist(String token, String playlistName);

    void deletePlaylist(int playlistId);

    void addTrackToPlaylist(int playlistId, int trackId, boolean offlineAvailable);

    void deleteTrackFromPlaylist(int playlistId, int trackId);
}
