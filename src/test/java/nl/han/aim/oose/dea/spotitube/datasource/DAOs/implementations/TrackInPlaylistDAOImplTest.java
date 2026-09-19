package nl.han.aim.oose.dea.spotitube.datasource.DAOs.implementations;

import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistDTO;
import nl.han.aim.oose.dea.spotitube.datasource.util.DatabaseProperties;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.sql.*;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TrackInPlaylistDAOImplTest {
    private final DatabaseProperties databaseProperties = new DatabaseProperties();
    private TrackInPlaylistDAOImpl sut;

    @BeforeEach
    void setUp() throws SQLException {
        sut = new TrackInPlaylistDAOImpl(databaseProperties);

        loadTestDatabase("testDB.sql");
    }

    private void loadTestDatabase(String filename) throws SQLException {
        RunScript.execute(DriverManager.getConnection(databaseProperties.connectionString()), new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("/" + filename))));
    }

    @Test
    void addTrackToPlaylistAddsTheTrackToThePlaylist() {
        //Arrange
        boolean trackInPlaylistBefore = false;
        boolean trackInPlaylistAfter = false;
        //Act
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT 1 FROM trackinplaylist WHERE trackId = ? AND playlistId = ?");
            statement.setInt(1, 2);
            statement.setInt(2, 1);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                trackInPlaylistBefore = true;
            }
            sut.addTrackToPlaylist(1, 2, false);
            statement = connection.prepareStatement("SELECT 1 FROM trackinplaylist WHERE trackId = ? AND playlistId = ?");
            statement.setInt(1, 2);
            statement.setInt(2, 1);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                trackInPlaylistAfter = true;
            }
            statement.close();
            connection.close();
        } catch (SQLException ignored) {
        }
        //Assert
        assertFalse(trackInPlaylistBefore);
        assertTrue(trackInPlaylistAfter);
    }

    @Test
    void deleteTrackFromPlaylistRemovesTrackFromPlaylist() {
        //Arrange
        boolean trackInPlaylist = false;
        //Act
        sut.deleteTrackFromPlaylist(1, 1);
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT 1 FROM trackinplaylist WHERE trackId = ? AND playlistId = ?");
            statement.setInt(1, 1);
            statement.setInt(1, 1);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                trackInPlaylist = true;
            }
            statement.close();
            connection.close();
        } catch (SQLException ignored) {
        }
        //Assert
        assertFalse(trackInPlaylist);
    }

}