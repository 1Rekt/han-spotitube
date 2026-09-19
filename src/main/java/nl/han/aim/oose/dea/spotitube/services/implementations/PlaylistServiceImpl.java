package nl.han.aim.oose.dea.spotitube.services.implementations;

import jakarta.inject.Inject;
import nl.han.aim.oose.dea.spotitube.datasource.DAOs.PlaylistDAO;
import nl.han.aim.oose.dea.spotitube.datasource.DAOs.TrackInPlaylistDAO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistsDTO;
import nl.han.aim.oose.dea.spotitube.services.PlaylistService;

public class PlaylistServiceImpl implements PlaylistService {
    PlaylistDAO playlistDAO;
    TrackInPlaylistDAO trackInPlaylistDAO;

    @Inject
    public PlaylistServiceImpl(PlaylistDAO playlistDAO, TrackInPlaylistDAO trackInPlaylistDAO) {
        this.playlistDAO = playlistDAO;
        this.trackInPlaylistDAO = trackInPlaylistDAO;
    }

    public PlaylistsDTO getAllPlaylists(String token) {
        return playlistDAO.getAllPlaylists(token);
    }

    public void editPlaylist(PlaylistDTO playlist) {
        playlistDAO.editPlaylist(playlist);
    }

    public void addPlaylist(String token, String playlistName) {
        playlistDAO.addPlaylist(token, playlistName);
    }

    public void deletePlaylist(int playlistId) {
        playlistDAO.deletePlaylist(playlistId);
    }

    public void addTrackToPlaylist(int playlistId, int trackId, boolean offlineAvailable) {
        trackInPlaylistDAO.addTrackToPlaylist(playlistId, trackId, offlineAvailable);
    }

    public void deleteTrackFromPlaylist(int playlistId, int trackId) {
        trackInPlaylistDAO.deleteTrackFromPlaylist(playlistId, trackId);
    }

}
