package nl.han.aim.oose.dea.spotitube.services.implementations;

import nl.han.aim.oose.dea.spotitube.datasource.DAOs.TrackDAO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.TrackDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.TracksDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackServiceImplTest {
    private final TrackDAO trackDAOMock = mock(TrackDAO.class);
    private TrackServiceImpl sut;

    private final int testPlaylistId = 1;
    private TracksDTO tracksDTO;
    @Captor
    private ArgumentCaptor<Integer> intOneCaptor;

    @BeforeEach
    void setUp() {
        sut = new TrackServiceImpl(trackDAOMock);

        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setId(1);
        trackDTO.setTitle("testTitle");
        trackDTO.setPerformer("testArtist");
        trackDTO.setDuration(60);
        trackDTO.setAlbum("testAlbum");
        trackDTO.setPlaycount(50);
        trackDTO.setPublicationDate("01-01-2024");
        trackDTO.setDescription("testDescription");
        trackDTO.setOfflineAvailable(true);
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        tracks.add(trackDTO);
        tracksDTO = new TracksDTO();
        tracksDTO.setTracks(tracks);
    }

    @Test
    void getAllTracksForPlaylistReturnsCorrectTracksDTO() {
        //Arrange
        TracksDTO expected = tracksDTO;
        //Act
        doReturn(expected).when(trackDAOMock).getAllTracksForPlaylist(anyInt());
        var actual = sut.getAllTracksForPlaylist(testPlaylistId);
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void getAllTracksForPlaylistPassesCorrectArgumentsToGetAllTracksForPlaylist() {
        //Arrange
        var expectedOne = testPlaylistId;
        //Act
        sut.getAllTracksForPlaylist(testPlaylistId);
        verify(trackDAOMock).getAllTracksForPlaylist(intOneCaptor.capture());
        var actualOne = intOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

    @Test
    void getAllAvailableTracksReturnsCorrectTracksDTO() {
        //Arrange
        TracksDTO expected = tracksDTO;
        //Act
        doReturn(expected).when(trackDAOMock).getAllAvailableTracks(anyInt());
        var actual = sut.getAllAvailableTracks(testPlaylistId);
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void getAllAvailableTracksPassesCorrectArgumentsToGetAllAvailableTracks() {
        //Arrange
        var expectedOne = testPlaylistId;
        //Act
        sut.getAllAvailableTracks(testPlaylistId);
        verify(trackDAOMock).getAllAvailableTracks(intOneCaptor.capture());
        var actualOne = intOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

}