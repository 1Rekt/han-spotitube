package nl.han.aim.oose.dea.spotitube.datasource.DAOs;

import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistsDTO;

public interface PlaylistDAO {
    PlaylistsDTO getAllPlaylists(String token);

    void editPlaylist(PlaylistDTO playlist);

    void addPlaylist(String token, String playlistName);

    void deletePlaylist(int playlistId);

    int getPlaylistsLength();
}
