package nl.han.aim.oose.dea.spotitube.datasource.DAOs;

import nl.han.aim.oose.dea.spotitube.datasource.DTOs.TracksDTO;

public interface TrackDAO {
    TracksDTO getAllTracksForPlaylist(int playlistId);

    TracksDTO getAllAvailableTracks(int playlistId);
}
