package nl.han.aim.oose.dea.spotitube.controllers;

import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistsDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.TrackDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.TracksDTO;
import nl.han.aim.oose.dea.spotitube.services.AuthenticationService;
import nl.han.aim.oose.dea.spotitube.services.PlaylistService;
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
class PlaylistControllerTest {

    public final AuthenticationService authenticationServiceMock = mock(AuthenticationService.class);
    public final PlaylistService playlistServiceMock = mock(PlaylistService.class);
    public final TrackService trackServiceMock = mock(TrackService.class);
    private PlaylistController sut;
    private PlaylistDTO playlistDTO;
    private PlaylistsDTO playlistsDTO;
    private TrackDTO trackDTO;
    private TracksDTO tracksDTO;
    private final String testToken = "testToken";
    @Captor
    private ArgumentCaptor<String> strOneCaptor;
    @Captor
    private ArgumentCaptor<Integer> intOneCaptor;
    @Captor
    private ArgumentCaptor<Integer> intTwoCaptor;
    @Captor
    private ArgumentCaptor<Boolean> booOneCaptor;
    @Captor
    private ArgumentCaptor<PlaylistDTO> playlistDTOOneCaptor;

    @BeforeEach
    public void setup() {
        sut = new PlaylistController();
        sut.setAuthenticationService(authenticationServiceMock);
        sut.setPlaylistService(playlistServiceMock);
        sut.setTrackService(trackServiceMock);

        playlistDTO = new PlaylistDTO();
        playlistDTO.setId(1);
        playlistDTO.setName("testName");
        playlistDTO.setOwner(true);
        playlistDTO.setTracks(new ArrayList<>());
        playlistsDTO = new PlaylistsDTO();
        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        playlists.add(playlistDTO);
        playlistsDTO.setPlaylists(playlists);
        trackDTO = new TrackDTO();
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
    void getAllPlaylistsReturnsStatusCode200() {
        //Arrange
        var expected = 200;
        //Act
        doNothing().when(authenticationServiceMock).verifyTokenExists(anyString());
        var actual = sut.getAllPlaylists(testToken).getStatus();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void getAllPlaylistsReturnsCorrectEntity() {
        //Arrange
        PlaylistsDTO expected = playlistsDTO;
        //Act
        doNothing().when(authenticationServiceMock).verifyTokenExists(anyString());
        doReturn(expected).when(playlistServiceMock).getAllPlaylists(anyString());
        var actual = sut.getAllPlaylists(testToken).getEntity();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void getAllPlaylistsReturnsStatusCode401() {
        //Arrange
        var expected = new TokenDoesntExistException();
        //Act
        doThrow(new TokenDoesntExistException()).when(authenticationServiceMock).verifyTokenExists(anyString());
        //Assert
        assertThrows(expected.getClass(), () -> sut.getAllPlaylists(testToken));
    }

    @Test
    void getAllPlaylistsPassesCorrectArgumentsToVerifyTokenExists() {
        //Arrange
        var expectedOne = testToken;
        //Act
        sut.getAllPlaylists(testToken);
        verify(authenticationServiceMock).verifyTokenExists(strOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

    @Test
    void getAllPlaylistsPassesCorrectArgumentsToGetAllPlaylists() {
        //Arrange
        var expectedOne = testToken;
        //Act
        sut.getAllPlaylists(testToken);
        verify(playlistServiceMock).getAllPlaylists(strOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

    @Test
    void getAllTracksForPlaylistReturnsStatusCode200() {
        //Arrange
        var expected = 200;
        //Act
        doNothing().when(authenticationServiceMock).verifyTokenExists(anyString());
        var actual = sut.getAllTracksForPlaylist(testToken, playlistDTO.getId()).getStatus();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void getAllTracksForPlaylistReturnsCorrectEntity() {
        //Arrange
        TracksDTO expected = tracksDTO;
        //Act
        doNothing().when(authenticationServiceMock).verifyTokenExists(anyString());
        doReturn(expected).when(trackServiceMock).getAllTracksForPlaylist(anyInt());
        var actual = sut.getAllTracksForPlaylist(testToken, playlistDTO.getId()).getEntity();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void getAllTracksForPlaylistReturnsStatusCode401() {
        //Arrange
        var expected = new TokenDoesntExistException();
        //Act
        doThrow(new TokenDoesntExistException()).when(authenticationServiceMock).verifyTokenExists(anyString());
        //Assert
        assertThrows(expected.getClass(), () -> sut.getAllTracksForPlaylist(testToken, playlistDTO.getId()));
    }

    @Test
    void getAllTracksForPlaylistPassesCorrectArgumentsToVerifyTokenExists() {
        //Arrange
        var expectedOne = testToken;
        //Act
        sut.getAllTracksForPlaylist(testToken, playlistDTO.getId());
        verify(authenticationServiceMock).verifyTokenExists(strOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

    @Test
    void getAllTracksForPlaylistPassesCorrectArgumentsToGetAllTracksForPlaylist() {
        //Arrange
        var expectedOne = playlistDTO.getId();
        //Act
        sut.getAllTracksForPlaylist(testToken, playlistDTO.getId());
        verify(trackServiceMock).getAllTracksForPlaylist(intOneCaptor.capture());
        var actualOne = intOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

    @Test
    void editPlaylistReturnsStatusCode201() {
        //Arrange
        var expected = 201;
        //Act
        doNothing().when(authenticationServiceMock).verifyTokenExists(anyString());
        doNothing().when(authenticationServiceMock).checkIfPlaylistOwner(anyString(), anyInt());
        doNothing().when(playlistServiceMock).editPlaylist(any());
        var actual = sut.editPlaylist(testToken, playlistDTO.getId(), playlistDTO).getStatus();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void editPlaylistReturnsCorrectEntity() {
        //Arrange
        PlaylistsDTO expected = playlistsDTO;
        //Act
        doNothing().when(authenticationServiceMock).verifyTokenExists(anyString());
        doNothing().when(authenticationServiceMock).checkIfPlaylistOwner(anyString(), anyInt());
        doNothing().when(playlistServiceMock).editPlaylist(any());
        doReturn(expected).when(playlistServiceMock).getAllPlaylists(anyString());
        var actual = sut.editPlaylist(testToken, playlistDTO.getId(), playlistDTO).getEntity();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void editPlaylistReturnsStatusCode401() {
        //Arrange
        var expected = new TokenDoesntExistException();
        //Act
        doThrow(new TokenDoesntExistException()).when(authenticationServiceMock).verifyTokenExists(anyString());
        //Assert
        assertThrows(expected.getClass(), () -> sut.editPlaylist(testToken, playlistDTO.getId(), playlistDTO));
    }

    @Test
    void editPlaylistReturnsStatusCode403() {
        //Arrange
        var expected = new NotOwnerOfPlaylistException();
        //Act
        doThrow(new NotOwnerOfPlaylistException()).when(authenticationServiceMock).checkIfPlaylistOwner(anyString(), anyInt());
        //Assert
        assertThrows(expected.getClass(), () -> sut.editPlaylist(testToken, playlistDTO.getId(), playlistDTO));
    }

    @Test
    void editPlaylistPassesCorrectArgumentsToVerifyTokenExists() {
        //Arrange
        var expectedOne = testToken;
        //Act
        sut.editPlaylist(testToken, playlistDTO.getId(), playlistDTO);
        verify(authenticationServiceMock).verifyTokenExists(strOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

    @Test
    void editPlaylistPassesCorrectArgumentsToCheckIfPlaylistOwner() {
        //Arrange
        var expectedOne = testToken;
        var expectedTwo = playlistDTO.getId();
        //Act
        sut.editPlaylist(testToken, playlistDTO.getId(), playlistDTO);
        verify(authenticationServiceMock).checkIfPlaylistOwner(strOneCaptor.capture(), intOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        var actualTwo = intOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
        assertEquals(expectedTwo, actualTwo);
    }

    @Test
    void editPlaylistPassesCorrectArgumentsToEditPlaylist() {
        //Arrange
        var expectedOne = playlistDTO;
        //Act
        sut.editPlaylist(testToken, playlistDTO.getId(), playlistDTO);
        verify(playlistServiceMock).editPlaylist(playlistDTOOneCaptor.capture());
        var actualOne = playlistDTOOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

    @Test
    void editPlaylistPassesCorrectArgumentsToGetAllPlaylists() {
        //Arrange
        var expectedOne = testToken;
        //Act
        sut.editPlaylist(testToken, playlistDTO.getId(), playlistDTO);
        verify(playlistServiceMock).getAllPlaylists(strOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

    @Test
    void addPlaylistReturnsStatusCode201() {
        //Arrange
        var expected = 201;
        //Act
        doNothing().when(authenticationServiceMock).verifyTokenExists(anyString());
        doNothing().when(authenticationServiceMock).checkIfPlaylistOwner(anyString(), anyInt());
        doNothing().when(playlistServiceMock).addPlaylist(anyString(), anyString());
        var actual = sut.addPlaylist(testToken, playlistDTO).getStatus();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void addPlaylistReturnsCorrectEntity() {
        //Arrange
        PlaylistsDTO expected = playlistsDTO;
        //Act
        doNothing().when(authenticationServiceMock).verifyTokenExists(anyString());
        doNothing().when(authenticationServiceMock).checkIfPlaylistOwner(anyString(), anyInt());
        doNothing().when(playlistServiceMock).editPlaylist(any());
        doReturn(expected).when(playlistServiceMock).getAllPlaylists(anyString());
        var actual = sut.addPlaylist(testToken, playlistDTO).getEntity();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void addPlaylistReturnsStatusCode401() {
        //Arrange
        var expected = new TokenDoesntExistException();
        //Act
        doThrow(new TokenDoesntExistException()).when(authenticationServiceMock).verifyTokenExists(anyString());
        //Assert
        assertThrows(expected.getClass(), () -> sut.addPlaylist(testToken, playlistDTO));
    }

    @Test
    void addPlaylistPassesCorrectArgumentsToVerifyTokenExists() {
        //Arrange
        var expectedOne = testToken;
        //Act
        sut.addPlaylist(testToken, playlistDTO);
        verify(authenticationServiceMock).verifyTokenExists(strOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

    @Test
    void addPlaylistPassesCorrectArgumentsToAddPlaylist() {
        //Arrange
        var expectedOne = testToken;
        //Act
        sut.addPlaylist(testToken, playlistDTO);
        verify(authenticationServiceMock).verifyTokenExists(strOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

    @Test
    void addPlaylistPassesCorrectGetAllPlaylists() {
        //Arrange
        var expectedOne = testToken;
        //Act
        sut.addPlaylist(testToken, playlistDTO);
        verify(playlistServiceMock).getAllPlaylists(strOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

    @Test
    void deletePlaylistReturnsStatusCode200() {
        //Arrange
        var expected = 200;
        //Act
        doNothing().when(authenticationServiceMock).verifyTokenExists(anyString());
        doNothing().when(authenticationServiceMock).checkIfPlaylistOwner(anyString(), anyInt());
        doNothing().when(playlistServiceMock).deletePlaylist(anyInt());
        var actual = sut.deletePlaylist(testToken, playlistDTO.getId()).getStatus();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void deletePlaylistReturnsCorrectEntity() {
        //Arrange
        PlaylistsDTO expected = playlistsDTO;
        //Act
        doNothing().when(authenticationServiceMock).verifyTokenExists(anyString());
        doNothing().when(authenticationServiceMock).checkIfPlaylistOwner(anyString(), anyInt());
        doNothing().when(playlistServiceMock).deletePlaylist(anyInt());
        doReturn(expected).when(playlistServiceMock).getAllPlaylists(anyString());
        var actual = sut.deletePlaylist(testToken, playlistDTO.getId()).getEntity();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void deletePlaylistReturnsStatusCode401() {
        //Arrange
        var expected = new TokenDoesntExistException();
        //Act
        doThrow(new TokenDoesntExistException()).when(authenticationServiceMock).verifyTokenExists(anyString());
        //Assert
        assertThrows(expected.getClass(), () -> sut.deletePlaylist(testToken, playlistDTO.getId()));
    }

    @Test
    void deletePlaylistReturnsStatusCode403() {
        //Arrange
        var expected = new NotOwnerOfPlaylistException();
        //Act
        doThrow(new NotOwnerOfPlaylistException()).when(authenticationServiceMock).checkIfPlaylistOwner(anyString(), anyInt());
        //Assert
        assertThrows(expected.getClass(), () -> sut.deletePlaylist(testToken, playlistDTO.getId()));
    }

    @Test
    void deletePlaylistPassesCorrectArgumentsToVerifyTokenExists() {
        //Arrange
        var expectedOne = testToken;
        //Act
        sut.deletePlaylist(testToken, playlistDTO.getId());
        verify(authenticationServiceMock).verifyTokenExists(strOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

    @Test
    void deletePlaylistPassesCorrectArgumentsToCheckIfPlaylistOwner() {
        //Arrange
        var expectedOne = testToken;
        var expectedTwo = playlistDTO.getId();
        //Act
        sut.deletePlaylist(testToken, playlistDTO.getId());
        verify(authenticationServiceMock).checkIfPlaylistOwner(strOneCaptor.capture(), intOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        var actualTwo = intOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
        assertEquals(expectedTwo, actualTwo);
    }

    @Test
    void deletePlaylistPassesCorrectArgumentsToDeletePlaylist() {
        //Arrange
        var expectedOne = playlistDTO.getId();
        //Act
        sut.deletePlaylist(testToken, playlistDTO.getId());
        verify(playlistServiceMock).deletePlaylist(intOneCaptor.capture());
        var actualOne = intOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

    @Test
    void deletePlaylistPassesCorrectArgumentsGetAllPlaylists() {
        //Arrange
        var expectedOne = testToken;
        //Act
        sut.deletePlaylist(testToken, playlistDTO.getId());
        verify(playlistServiceMock).getAllPlaylists(strOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

    @Test
    void addTrackToPlaylistReturnsStatusCode201() {
        //Arrange
        var expected = 201;
        //Act
        doNothing().when(authenticationServiceMock).verifyTokenExists(anyString());
        doNothing().when(authenticationServiceMock).checkIfPlaylistOwner(anyString(), anyInt());
        doNothing().when(playlistServiceMock).addTrackToPlaylist(anyInt(), anyInt(), anyBoolean());
        var actual = sut.addTrackToPlaylist(testToken, playlistDTO.getId(), trackDTO).getStatus();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void addTrackToPlaylistReturnsCorrectEntity() {
        //Arrange
        PlaylistsDTO expected = playlistsDTO;
        //Act
        doNothing().when(authenticationServiceMock).verifyTokenExists(anyString());
        doNothing().when(authenticationServiceMock).checkIfPlaylistOwner(anyString(), anyInt());
        doNothing().when(playlistServiceMock).addTrackToPlaylist(anyInt(), anyInt(), anyBoolean());
        doReturn(expected).when(playlistServiceMock).getAllPlaylists(anyString());
        var actual = sut.addTrackToPlaylist(testToken, playlistDTO.getId(), trackDTO).getEntity();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void addTrackToPlaylistReturnsStatusCode401() {
        //Arrange
        var expected = new TokenDoesntExistException();
        //Act
        doThrow(new TokenDoesntExistException()).when(authenticationServiceMock).verifyTokenExists(anyString());
        //Assert
        assertThrows(expected.getClass(), () -> sut.addTrackToPlaylist(testToken, playlistDTO.getId(), trackDTO));
    }

    @Test
    void addTrackToPlaylistReturnsStatusCode403() {
        //Arrange
        var expected = new NotOwnerOfPlaylistException();
        //Act
        doThrow(new NotOwnerOfPlaylistException()).when(authenticationServiceMock).checkIfPlaylistOwner(anyString(), anyInt());
        //Assert
        assertThrows(expected.getClass(), () -> sut.addTrackToPlaylist(testToken, playlistDTO.getId(), trackDTO));
    }

    @Test
    void addTrackToPlaylistPassesCorrectArgumentsToVerifyTokenExists() {
        //Arrange
        var expectedOne = testToken;
        //Act
        sut.deletePlaylist(testToken, playlistDTO.getId());
        verify(authenticationServiceMock).verifyTokenExists(strOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

    @Test
    void addTrackToPlaylistPassesCorrectArgumentsToCheckIfPlaylistOwner() {
        //Arrange
        var expectedOne = testToken;
        var expectedTwo = playlistDTO.getId();
        //Act
        sut.addTrackToPlaylist(testToken, playlistDTO.getId(), trackDTO);
        verify(authenticationServiceMock).checkIfPlaylistOwner(strOneCaptor.capture(), intOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        var actualTwo = intOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
        assertEquals(expectedTwo, actualTwo);
    }

    @Test
    void addTrackToPlaylistPassesCorrectArgumentsToDeletePlaylist() {
        //Arrange
        var expectedOne = playlistDTO.getId();
        var expectedTwo = trackDTO.getId();
        var expectedThree = trackDTO.isOfflineAvailable();
        //Act
        sut.addTrackToPlaylist(testToken, playlistDTO.getId(), trackDTO);
        verify(playlistServiceMock).addTrackToPlaylist(intOneCaptor.capture(), intTwoCaptor.capture(), booOneCaptor.capture());
        var actualOne = intOneCaptor.getValue();
        var actualTwo = intTwoCaptor.getValue();
        var actualThree = booOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
        assertEquals(expectedTwo, actualTwo);
        assertEquals(expectedThree, actualThree);
    }

    @Test
    void addTrackToPlaylistPassesCorrectArgumentsGetAllPlaylists() {
        //Arrange
        var expectedOne = testToken;
        //Act
        sut.addTrackToPlaylist(testToken, playlistDTO.getId(), trackDTO);
        verify(playlistServiceMock).getAllPlaylists(strOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

    @Test
    void deleteTrackFromPlaylistReturnsStatusCode200() {
        //Arrange
        var expected = 200;
        //Act
        doNothing().when(authenticationServiceMock).verifyTokenExists(anyString());
        doNothing().when(authenticationServiceMock).checkIfPlaylistOwner(anyString(), anyInt());
        doNothing().when(playlistServiceMock).deleteTrackFromPlaylist(anyInt(), anyInt());
        var actual = sut.deleteTrackFromPlaylist(testToken, playlistDTO.getId(), trackDTO.getId()).getStatus();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void deleteTrackFromPlaylistReturnsCorrectEntity() {
        //Arrange
        PlaylistsDTO expected = playlistsDTO;
        //Act
        doNothing().when(authenticationServiceMock).verifyTokenExists(anyString());
        doNothing().when(authenticationServiceMock).checkIfPlaylistOwner(anyString(), anyInt());
        doNothing().when(playlistServiceMock).deleteTrackFromPlaylist(anyInt(), anyInt());
        doReturn(expected).when(playlistServiceMock).getAllPlaylists(anyString());
        var actual = sut.deleteTrackFromPlaylist(testToken, playlistDTO.getId(), trackDTO.getId()).getEntity();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void deleteTrackFromPlaylistReturnsStatusCode401() {
        //Arrange
        var expected = new TokenDoesntExistException();
        //Act
        doThrow(new TokenDoesntExistException()).when(authenticationServiceMock).verifyTokenExists(anyString());
        //Assert
        assertThrows(expected.getClass(), () -> sut.deleteTrackFromPlaylist(testToken, playlistDTO.getId(), trackDTO.getId()));
    }

    @Test
    void deleteTrackFromPlaylistReturnsStatusCode403() {
        //Arrange
        var expected = new NotOwnerOfPlaylistException();
        //Act
        doThrow(new NotOwnerOfPlaylistException()).when(authenticationServiceMock).checkIfPlaylistOwner(anyString(), anyInt());
        //Assert
        assertThrows(expected.getClass(), () -> sut.deleteTrackFromPlaylist(testToken, playlistDTO.getId(), trackDTO.getId()));
    }

    @Test
    void deleteTrackFromPlaylistPassesCorrectArgumentsToVerifyTokenExists() {
        //Arrange
        var expectedOne = testToken;
        //Act
        sut.deleteTrackFromPlaylist(testToken, playlistDTO.getId(), trackDTO.getId());
        verify(authenticationServiceMock).verifyTokenExists(strOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
    }

    @Test
    void deleteTrackFromPlaylistPassesCorrectArgumentsToCheckIfPlaylistOwner() {
        //Arrange
        var expectedOne = testToken;
        var expectedTwo = playlistDTO.getId();
        //Act
        sut.deleteTrackFromPlaylist(testToken, playlistDTO.getId(), trackDTO.getId());
        verify(authenticationServiceMock).checkIfPlaylistOwner(strOneCaptor.capture(), intOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        var actualTwo = intOneCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
        assertEquals(expectedTwo, actualTwo);
    }

    @Test
    void deleteTrackFromPlaylistPassesCorrectArgumentsToDeleteTrackFromPlaylist() {
        //Arrange
        var expectedOne = playlistDTO.getId();
        var expectedTwo = trackDTO.getId();
        //Act
        sut.deleteTrackFromPlaylist(testToken, playlistDTO.getId(), trackDTO.getId());
        verify(playlistServiceMock).deleteTrackFromPlaylist(intOneCaptor.capture(), intTwoCaptor.capture());
        var actualOne = intOneCaptor.getValue();
        var actualTwo = intTwoCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
        assertEquals(expectedTwo, actualTwo);
    }

    @Test
    void deleteTrackFromPlaylistPassesCorrectArgumentsGetAllPlaylists() {
        //Arrange
        var expectedOne = playlistDTO.getId();
        var expectedTwo = trackDTO.getId();
        //Act
        sut.deleteTrackFromPlaylist(testToken, playlistDTO.getId(), trackDTO.getId());
        verify(playlistServiceMock).deleteTrackFromPlaylist(intOneCaptor.capture(), intTwoCaptor.capture());
        var actualOne = intOneCaptor.getValue();
        var actualTwo = intTwoCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
        assertEquals(expectedTwo, actualTwo);
    }

}