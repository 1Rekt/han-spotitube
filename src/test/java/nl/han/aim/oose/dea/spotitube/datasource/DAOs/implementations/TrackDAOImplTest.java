package nl.han.aim.oose.dea.spotitube.datasource.DAOs.implementations;

import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistsDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.TrackDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.TracksDTO;
import nl.han.aim.oose.dea.spotitube.datasource.mappers.TrackMapper;
import nl.han.aim.oose.dea.spotitube.datasource.util.DatabaseProperties;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrackDAOImplTest {
    private final DatabaseProperties databaseProperties = new DatabaseProperties();
    private final TrackMapper trackMapper = new TrackMapper();
    private TrackDAOImpl sut;

    private TracksDTO tracksDTO;
    private TrackDTO trackTwo;
    private final int testPlaylistId = 1;

    @BeforeEach
    void setUp() throws SQLException {
        sut = new TrackDAOImpl(databaseProperties, trackMapper);

        loadTestDatabase("testDB.sql");
        tracksDTO = new TracksDTO();
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
        trackTwo = new TrackDTO();
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
        tracksDTO.setTracks(tracksOne);
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setId(1);
        playlistDTO.setName("Best Playlist");
        playlistDTO.setOwner(true);
        playlistDTO.setTracks(tracksOne);

    }

    private void loadTestDatabase(String filename) throws SQLException {
        RunScript.execute(DriverManager.getConnection(databaseProperties.connectionString()), new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("/" + filename))));
    }

    @Test
    void getAllTracksForPlaylistReturnsAllTracksForThatPlaylist() {
        //Arrange
        var expected = tracksDTO;
        //Act
        var actual = sut.getAllTracksForPlaylist(testPlaylistId);
        //Assert
        assertEquals(expected.getTracks().get(0).getId(), actual.getTracks().get(0).getId());
        assertEquals(expected.getTracks().get(1).getId(), actual.getTracks().get(1).getId());
    }

    @Test
    void getAllAvailableTracksReturnsAllTracksNotInPlaylist() {
        //Arrange
        var expected = trackTwo;
        //Act
        var actual = sut.getAllAvailableTracks(testPlaylistId);
        //Assert
        assertEquals(expected.getId(), actual.getTracks().get(0).getId());
    }

}