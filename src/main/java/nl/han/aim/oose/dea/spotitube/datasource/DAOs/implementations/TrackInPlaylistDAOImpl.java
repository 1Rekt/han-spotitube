package nl.han.aim.oose.dea.spotitube.datasource.DAOs.implementations;

import jakarta.inject.Inject;
import nl.han.aim.oose.dea.spotitube.datasource.DAOs.TrackInPlaylistDAO;
import nl.han.aim.oose.dea.spotitube.datasource.util.DatabaseProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrackInPlaylistDAOImpl implements TrackInPlaylistDAO {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final DatabaseProperties databaseProperties;

    @Inject
    public TrackInPlaylistDAOImpl(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public void addTrackToPlaylist(int playlistId, int trackId, boolean offlineAvailable) {
        try (Connection connection = DriverManager.getConnection(databaseProperties.connectionString())) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO trackinplaylist (trackId, playlistId, offline_available) VALUES (?, ?, ?)");
            statement.setInt(1, trackId);
            statement.setInt(2, playlistId);
            statement.setBoolean(3, offlineAvailable);
            statement.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Can't insert track with id " + trackId + "for playlist with id " + playlistId, e);
        }
    }

    public void deleteTrackFromPlaylist(int playlistId, int trackId) {
        try (Connection connection = DriverManager.getConnection(databaseProperties.connectionString())) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM trackinplaylist WHERE playlistId = ? AND trackId = ?");
            statement.setInt(1, playlistId);
            statement.setInt(2, trackId);
            statement.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Can't delete track with id " + trackId + "from playlist with id " + playlistId, e);
        }
    }

}
