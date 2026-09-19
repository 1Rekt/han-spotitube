package nl.han.aim.oose.dea.spotitube.services.implementations;

import jakarta.inject.Inject;
import nl.han.aim.oose.dea.spotitube.datasource.DAOs.TrackDAO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.TracksDTO;
import nl.han.aim.oose.dea.spotitube.services.TrackService;

public class TrackServiceImpl implements TrackService {
    TrackDAO trackDAO;

    @Inject
    public TrackServiceImpl(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    public TracksDTO getAllTracksForPlaylist(int playlistId) {
        return trackDAO.getAllTracksForPlaylist(playlistId);
    }

    public TracksDTO getAllAvailableTracks(int playlistId) {
        return trackDAO.getAllAvailableTracks(playlistId);
    }

}
