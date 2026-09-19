package nl.han.aim.oose.dea.spotitube.datasource.DAOs.implementations;

import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistsDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.TrackDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.TracksDTO;
import nl.han.aim.oose.dea.spotitube.datasource.mappers.PlaylistMapper;
import nl.han.aim.oose.dea.spotitube.datasource.util.DatabaseProperties;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistDAOImplTest {
    private final DatabaseProperties databaseProperties = new DatabaseProperties();
    private final PlaylistMapper playlistMapper = new PlaylistMapper();
    private PlaylistDAOImpl sut;

    private final String testToken = "b34797ea-7977-40dd-af91-794397843e65";
    private PlaylistDTO playlistDTOOne;
    private PlaylistsDTO playlistsDTO;

    @BeforeEach
    void setUp() throws SQLException {
        sut = new PlaylistDAOImpl(databaseProperties, playlistMapper);

        loadTestDatabase("testDB.sql");
        TrackDTO trackOne = new TrackDTO();
        trackOne.setId(1);
        trackOne.setTitle("Bohemian Rhapsody");
        trackOne.setPerformer("Queen");
        trackOne.setDuration(354);
        trackOne.setAlbum("A Night at the Opera");
        trackOne.setPlaycount(10000);
        trackOne.setPublicationDate("1975-10-31");
        trackOne.setDescription("Iconic rock song by Queen");
        trackOne.setOfflineAvailable(false);
        TrackDTO trackTwo = new TrackDTO();
        trackTwo.setId(2);
        trackTwo.setTitle("Stairway to Heaven");
        trackTwo.setPerformer("Led Zeppelin");
        trackTwo.setDuration(480);
        trackTwo.setAlbum(null);
        trackTwo.setPlaycount(0);
        trackTwo.setPublicationDate(null);
        trackTwo.setDescription(null);
        trackTwo.setOfflineAvailable(false);
        TrackDTO trackThree = new TrackDTO();
        trackThree.setId(3);
        trackThree.setTitle("Hotel California");
        trackThree.setPerformer("Eagles");
        trackThree.setDuration(390);
        trackThree.setAlbum(null);
        trackThree.setPlaycount(0);
        trackThree.setPublicationDate(null);
        trackThree.setDescription(null);
        trackThree.setOfflineAvailable(false);
        ArrayList<TrackDTO> tracksOne = new ArrayList<>();
        tracksOne.add(trackOne);
        tracksOne.add(trackThree);
        TracksDTO tracksDTOOne = new TracksDTO();
        tracksDTOOne.setTracks(tracksOne);
        ArrayList<TrackDTO> tracksTwo = new ArrayList<>();
        tracksTwo.add(trackOne);
        tracksTwo.add(trackTwo);
        tracksTwo.add(trackThree);
        playlistDTOOne = new PlaylistDTO();
        playlistDTOOne.setId(1);
        playlistDTOOne.setName("Best Playlist");
        playlistDTOOne.setOwner(true);
        playlistDTOOne.setTracks(tracksOne);
        PlaylistDTO playlistDTOTwo = new PlaylistDTO();
        playlistDTOTwo.setId(2);
        playlistDTOTwo.setName("Not My Playlist");
        playlistDTOTwo.setOwner(false);
        playlistDTOTwo.setTracks(tracksTwo);
        playlistsDTO = new PlaylistsDTO();
        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        playlists.add(playlistDTOOne);
        playlists.add(playlistDTOTwo);
        playlistsDTO.setPlaylists(playlists);
    }

    private void loadTestDatabase(String filename) throws SQLException {
        RunScript.execute(DriverManager.getConnection(databaseProperties.connectionString()), new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("/" + filename))));
    }

    @Test
    void getAllPlaylistsReturnsAllPlaylists() {
        //Arrange
        var expected = playlistsDTO;
        //Act
        var actual = sut.getAllPlaylists(testToken);
        //Assert
        assertEquals(expected.getPlaylists().get(0).getId(), actual.getPlaylists().get(0).getId());
        assertEquals(expected.getPlaylists().get(1).getId(), actual.getPlaylists().get(1).getId());
    }

    @Test
    void editPlaylistChangesPlaylistToNewValues() {
        //Arrange
        var oldPlaylistName = "";
        var newPlaylistName = "";
        //Act
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM playlist WHERE id = ?");
            statement.setInt(1, 1);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                oldPlaylistName = resultSet.getString("name");
            }
            PlaylistDTO playlistDTO = new PlaylistDTO();
            playlistDTO.setId(1);
            playlistDTO.setName("new name");
            sut.editPlaylist(playlistDTO);
            statement = connection.prepareStatement("SELECT name FROM playlist WHERE id = ?");
            statement.setInt(1, 1);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                newPlaylistName = resultSet.getString("name");
            }
            statement.close();
            connection.close();
        } catch (SQLException ignored) {
        }
        //Assert
        assertNotEquals(oldPlaylistName, newPlaylistName);
        assertEquals(newPlaylistName, "new name");
    }

    @Test
    void addPlaylistAddsANewPlaylist() {
        //Arrange
        var expected = "nice name";
        //Act
        sut.addPlaylist(testToken, expected);
        var actual = "";
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM playlist WHERE id = ?");
            statement.setInt(1, 3);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                actual = resultSet.getString("name");
            }
            statement.close();
            connection.close();
        } catch (SQLException ignored) {
        }
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void deletePlaylistRemovesPlaylist() {
        //Arrange
        var actual = true;
        //Act
        sut.deletePlaylist(1);
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT 1 FROM playlist WHERE id = ?");
            statement.setInt(1, 1);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                actual = false;
            }
            statement.close();
            connection.close();
        } catch (SQLException ignored) {
        }
        //Assert
        assertTrue(actual);
    }

    @Test
    void getPlaylistsLengthReturnsTotalPlaylistsLength() {
        //Arrange
        var expected = 1968;
        //Act
        var actual = sut.getPlaylistsLength();
        //Assert
        assertEquals(expected, actual);
    }

}