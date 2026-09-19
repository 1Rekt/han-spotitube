package nl.han.aim.oose.dea.spotitube.controllers;

import nl.han.aim.oose.dea.spotitube.datasource.DTOs.TrackDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.TracksDTO;
import nl.han.aim.oose.dea.spotitube.services.AuthenticationService;
import nl.han.aim.oose.dea.spotitube.services.TrackService;
import nl.han.aim.oose.dea.spotitube.services.exceptions.NotOwnerOfPlaylistException;
import nl.han.aim.oose.dea.spotitube.services.exceptions.TokenDoesntExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackControllerTest {

    public final AuthenticationService authenticationServiceMock = mock(AuthenticationService.class);
    public final TrackService trackServiceMock = mock(TrackService.class);
    private TrackController sut;
    private TracksDTO tracksDTO;
    private final String testToken = "testToken";
    private final int testPlaylistId = 1;
    @Captor
    private ArgumentCaptor<String> strOneCaptor;
    @Captor
    private ArgumentCaptor<Integer> intOneCaptor;

    @BeforeEach
    void setUp() {
        sut = new TrackController();
        sut.setAuthenticationService(authenticationServiceMock);
        sut.setTrackService(trackServiceMock);

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
    void getAllAvailableTracksReturnsStatusCode200() {
        //Arrange
        var expected = 200;
        //Act
        doNothing().when(authenticationServiceMock).verifyTokenExists(anyString());
        doNothing().when(authenticationServiceMock).checkIfPlaylistOwner(anyString(), anyInt());
        var actual = sut.getAllAvailableTracks(testToken, testPlaylistId).getStatus();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void getAllAvailableTracksReturnsCorrectEntity() {
        //Arrange
        TracksDTO expected = tracksDTO;
        //Act
        doNothing().when(authenticationServiceMock).verifyTokenExists(anyString());
        doNothing().when(authenticationServiceMock).checkIfPlaylistOwner(anyString(), anyInt());
        doReturn(expected).when(trackServiceMock).getAllAvailableTracks(anyInt());
        var actual = sut.getAllAvailableTracks(testToken, testPlaylistId).getEntity();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void getAllAvailableTracksReturnsStatusCode401() {
        //Arrange
        var expected = new TokenDoesntExistException();
        //Act
        doThrow(new TokenDoesntExistException()).when(authenticationServiceMock).verifyTokenExists(anyString());
        //Assert
        assertThrows(expected.getClass(), () -> sut.getAllAvailableTracks(testToken, testPlaylistId));
    }

    @Test
    void getAllAvailableTracksReturnsStatusCode403() {
        //Arrange
        var expected = new NotOwnerOfPlaylistException();
        //Act
        doThrow(new NotOwnerOfPlaylistException()).when(authenticationServiceMock).checkIfPlaylistOwner(anyString(), anyInt());
        //Assert
        assertThrows(expected.getClass(), () -> sut.getAllAvailableTracks(testToken, testPlaylistId));
    }

    @Test
    void getAllAvailableTracksPassesCorrectArgumentsToVerifyTokenExists() {
        //Arrange
        var expectedOne = testToken;
        //Act
        sut.getAllAvailableTracks(testToken, testPlaylistId);
        verify(authenticationServiceMock).verifyTokenExists(strOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

    @Test
    void getAllAvailableTracksPassesCorrectArgumentsToCheckIfPlaylistOwner() {
        //Arrange
        var expectedOne = testToken;
        var expectedTwo = testPlaylistId;
        //Act
        sut.getAllAvailableTracks(testToken, testPlaylistId);
        verify(authenticationServiceMock).checkIfPlaylistOwner(strOneCaptor.capture(), intOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        var actualTwo = intOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
        assertEquals(expectedTwo, actualTwo);
    }

    @Test
    void getAllAvailableTracksPassesCorrectArgumentsToGetAllAvailableTracks() {
        //Arrange
        var expectedOne = testPlaylistId;
        //Act
        sut.getAllAvailableTracks(testToken, testPlaylistId);
        verify(trackServiceMock).getAllAvailableTracks(intOneCaptor.capture());
        var actualOne = intOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

}