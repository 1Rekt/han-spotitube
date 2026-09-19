package nl.han.aim.oose.dea.spotitube.datasource.DAOs.implementations;

import jakarta.inject.Inject;
import nl.han.aim.oose.dea.spotitube.datasource.DAOs.TrackDAO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.TrackDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.TracksDTO;
import nl.han.aim.oose.dea.spotitube.datasource.mappers.TrackMapper;
import nl.han.aim.oose.dea.spotitube.datasource.util.DatabaseProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrackDAOImpl implements TrackDAO {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final DatabaseProperties databaseProperties;
    private final TrackMapper trackMapper;

    @Inject
    public TrackDAOImpl(DatabaseProperties databaseProperties, TrackMapper trackMapper) {
        this.databaseProperties = databaseProperties;
        this.trackMapper = trackMapper;
    }

    public TracksDTO getAllTracksForPlaylist(int playlistId) {
        TracksDTO allTracksInPlaylist = new TracksDTO();
        ArrayList<TrackDTO> dbTracksInPlaylist = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT id, title, performer, duration, album, playcount, publication_date, description, offline_available FROM track INNER JOIN trackInPlaylist ON id = trackId WHERE playlistId = ?");
            statement.setInt(1, playlistId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                dbTracksInPlaylist.add(trackMapper.mapTrack(resultSet));
            }
            allTracksInPlaylist.setTracks(dbTracksInPlaylist);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return allTracksInPlaylist;
    }

    public TracksDTO getAllAvailableTracks(int playlistId) {
        TracksDTO allAvailableTracks = new TracksDTO();
        ArrayList<TrackDTO> dbAvailableTracks = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT id, title, performer, duration, album, playcount, publication_date, description, offline_available FROM track INNER JOIN trackInPlaylist tip ON id = trackId WHERE trackId NOT IN (SELECT trackId from trackinplaylist WHERE playlistId = ?)");
            statement.setInt(1, playlistId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                dbAvailableTracks.add(trackMapper.mapTrack(resultSet));
            }
            allAvailableTracks.setTracks(dbAvailableTracks);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return allAvailableTracks;
    }

}
