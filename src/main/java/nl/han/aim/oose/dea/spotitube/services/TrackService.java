package nl.han.aim.oose.dea.spotitube.services;

import nl.han.aim.oose.dea.spotitube.datasource.DTOs.TracksDTO;

public interface TrackService {
    TracksDTO getAllTracksForPlaylist(int playlistId);

    TracksDTO getAllAvailableTracks(int playlistId);
}
