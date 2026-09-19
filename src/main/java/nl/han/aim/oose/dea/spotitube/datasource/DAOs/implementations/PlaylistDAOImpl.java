package nl.han.aim.oose.dea.spotitube.datasource.DAOs.implementations;

import jakarta.inject.Inject;
import nl.han.aim.oose.dea.spotitube.datasource.DAOs.PlaylistDAO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistsDTO;
import nl.han.aim.oose.dea.spotitube.datasource.mappers.PlaylistMapper;
import nl.han.aim.oose.dea.spotitube.datasource.util.DatabaseProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaylistDAOImpl implements PlaylistDAO {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final DatabaseProperties databaseProperties;
    private final PlaylistMapper playlistMapper;

    @Inject
    public PlaylistDAOImpl(DatabaseProperties databaseProperties, PlaylistMapper playlistMapper) {
        this.databaseProperties = databaseProperties;
        this.playlistMapper = playlistMapper;
    }

    public PlaylistsDTO getAllPlaylists(String token) {
        PlaylistsDTO allPlaylists = new PlaylistsDTO();
        ArrayList<PlaylistDTO> dbPlaylists = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT id, name, token FROM playlist INNER JOIN user ON owner = username");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                dbPlaylists.add(playlistMapper.mapPlaylist(resultSet, token));
            }
            allPlaylists.setPlaylists(dbPlaylists);
            allPlaylists.setLength(getPlaylistsLength());
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return allPlaylists;
    }

    public void editPlaylist(PlaylistDTO playlist) {
        try (Connection connection = DriverManager.getConnection(databaseProperties.connectionString())) {
            PreparedStatement statement = connection.prepareStatement("UPDATE playlist SET name = ? WHERE id = ?");
            statement.setString(1, playlist.getName());
            statement.setInt(2, playlist.getId());
            statement.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Can't update playlist " + playlist, e);
        }
    }

    public void addPlaylist(String token, String playlistName) {
        try (Connection connection = DriverManager.getConnection(databaseProperties.connectionString())) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO playlist (id, name, owner) SELECT (SELECT (MAX(id) + 1) FROM playlist), ?, (SELECT username FROM user WHERE token = ?)");
            statement.setString(1, playlistName);
            statement.setString(2, token);
            statement.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Can't insert playlist with name " + playlistName, e);
        }
    }

    public void deletePlaylist(int playlistId) {
        try (Connection connection = DriverManager.getConnection(databaseProperties.connectionString())) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM playlist WHERE id = ?");
            statement.setInt(1, playlistId);
            statement.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Can't delete playlist with id " + playlistId, e);
        }
    }

    public int getPlaylistsLength() {
        int playlistsLength = 0;
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT duration FROM trackinplaylist JOIN track on id = trackId");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                playlistsLength += resultSet.getInt("duration");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return playlistsLength;
    }

}
