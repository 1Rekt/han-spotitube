package nl.han.aim.oose.dea.spotitube.services.implementations;

import nl.han.aim.oose.dea.spotitube.datasource.DAOs.PlaylistDAO;
import nl.han.aim.oose.dea.spotitube.datasource.DAOs.TrackInPlaylistDAO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistsDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceImplTest {
    private final PlaylistDAO playlistDAOMock = mock(PlaylistDAO.class);
    private final TrackInPlaylistDAO trackInPlaylistDAOMock = mock(TrackInPlaylistDAO.class);
    private PlaylistServiceImpl sut;

    private final String testToken = "testToken";
    private final int testTrackId = 1;
    private final boolean testOfflineAvailable = true;
    private PlaylistDTO playlistDTO;
    private PlaylistsDTO playlistsDTO;
    @Captor
    private ArgumentCaptor<String> strOneCaptor;
    @Captor
    private ArgumentCaptor<String> strTwoCaptor;
    @Captor
    private ArgumentCaptor<PlaylistDTO> playlistOneCaptor;
    @Captor
    private ArgumentCaptor<Integer> intOneCaptor;
    @Captor
    private ArgumentCaptor<Integer> intTwoCaptor;
    @Captor
    private ArgumentCaptor<Boolean> boolOneCaptor;

    @BeforeEach
    void setUp() {
        sut = new PlaylistServiceImpl(playlistDAOMock, trackInPlaylistDAOMock);

        playlistDTO = new PlaylistDTO();
        playlistDTO.setId(1);
        playlistDTO.setName("testName");
        playlistDTO.setOwner(true);
        playlistDTO.setTracks(new ArrayList<>());
        playlistsDTO = new PlaylistsDTO();
        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        playlists.add(playlistDTO);
        playlistsDTO.setPlaylists(playlists);
    }

    @Test
    void getAllPlaylistsReturnsCorrectPlaylistsDTO() {
        //Arrange
        PlaylistsDTO expected = playlistsDTO;
        //Act
        doReturn(expected).when(playlistDAOMock).getAllPlaylists(anyString());
        var actual = sut.getAllPlaylists(testToken);
        //Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllPlaylistsPassesCorrectArgumentsToGetAllPlaylists() {
        //Arrange
        var expectedOne = "testToken";
        //Act
        sut.getAllPlaylists(testToken);
        verify(playlistDAOMock).getAllPlaylists(strOneCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        //Assert
        Assertions.assertEquals(expectedOne, actualOne);
    }

    @Test
    void editPlaylistsPassesCorrectArgumentsToEditPlaylist() {
        //Arrange
        var expectedOne = playlistDTO;
        //Act
        sut.editPlaylist(playlistDTO);
        verify(playlistDAOMock).editPlaylist(playlistOneCaptor.capture());
        var actualOne = playlistOneCaptor.getValue();
        //Assert
        Assertions.assertEquals(expectedOne, actualOne);
    }

    @Test
    void addPlaylistsPassesCorrectArgumentsToAddPlaylist() {
        //Arrange
        var expectedOne = testToken;
        var expectedTwo = playlistDTO.getName();
        //Act
        sut.addPlaylist(testToken, playlistDTO.getName());
        verify(playlistDAOMock).addPlaylist(strOneCaptor.capture(), strTwoCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        var actualTwo = strTwoCaptor.getValue();
        //Assert
        Assertions.assertEquals(expectedOne, actualOne);
        Assertions.assertEquals(expectedTwo, actualTwo);
    }

    @Test
    void deletePlaylistsPassesCorrectArgumentsToDeletePlaylist() {
        //Arrange
        var expectedOne = playlistDTO.getId();
        //Act
        sut.deletePlaylist(playlistDTO.getId());
        verify(playlistDAOMock).deletePlaylist(intOneCaptor.capture());
        var actualOne = intOneCaptor.getValue();
        //Assert
        Assertions.assertEquals(expectedOne, actualOne);
    }

    @Test
    void addTrackToPlaylistsPassesCorrectArgumentsToAddTrackToPlaylist() {
        //Arrange
        var expectedOne = playlistDTO.getId();
        var expectedTwo = testTrackId;
        var expectedThree = testOfflineAvailable;
        //Act
        sut.addTrackToPlaylist(playlistDTO.getId(), testTrackId, testOfflineAvailable);
        verify(trackInPlaylistDAOMock).addTrackToPlaylist(intOneCaptor.capture(), intTwoCaptor.capture(), boolOneCaptor.capture());
        var actualOne = intOneCaptor.getValue();
        var actualTwo = intTwoCaptor.getValue();
        var actualThree = boolOneCaptor.getValue();
        //Assert
        Assertions.assertEquals(expectedOne, actualOne);
        Assertions.assertEquals(expectedTwo, actualTwo);
        Assertions.assertEquals(expectedThree, actualThree);
    }

    @Test
    void deleteTrackFromPlaylistsPassesCorrectArgumentsToDeleteTrackFromPlaylist() {
        //Arrange
        var expectedOne = playlistDTO.getId();
        var expectedTwo = testTrackId;
        //Act
        sut.deleteTrackFromPlaylist(playlistDTO.getId(), testTrackId);
        verify(trackInPlaylistDAOMock).deleteTrackFromPlaylist(intOneCaptor.capture(), intTwoCaptor.capture());
        var actualOne = intOneCaptor.getValue();
        var actualTwo = intTwoCaptor.getValue();
        //Assert
        Assertions.assertEquals(expectedOne, actualOne);
        Assertions.assertEquals(expectedTwo, actualTwo);
    }

}