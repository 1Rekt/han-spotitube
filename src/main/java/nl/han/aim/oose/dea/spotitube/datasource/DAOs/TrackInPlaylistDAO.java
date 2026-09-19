package nl.han.aim.oose.dea.spotitube.datasource.DAOs;

public interface TrackInPlaylistDAO {
    void addTrackToPlaylist(int playlistId, int trackId, boolean offlineAvailable);

    void deleteTrackFromPlaylist(int playlistId, int trackId);
}
