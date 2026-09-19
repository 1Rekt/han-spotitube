package nl.han.aim.oose.dea.spotitube.services.implementations;

import nl.han.aim.oose.dea.spotitube.datasource.DAOs.AuthenticationDAO;
import nl.han.aim.oose.dea.spotitube.services.exceptions.NotOwnerOfPlaylistException;
import nl.han.aim.oose.dea.spotitube.services.exceptions.TokenDoesntExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {
    private final AuthenticationDAO authenticationDAOMock = mock(AuthenticationDAO.class);
    private AuthenticationServiceImpl sut;

    private final String testToken = "testToken";
    private final int testPlaylistId = 1;
    @Captor
    private ArgumentCaptor<String> strOneCaptor;
    @Captor
    private ArgumentCaptor<Integer> intOneCaptor;

    @BeforeEach
    void setUp() {
        sut = new AuthenticationServiceImpl(authenticationDAOMock);
    }

    @Test
    void verifyTokenExistsDoesNotThrowException() {
        //Arrange
        //Act
        doReturn(true).when(authenticationDAOMock).verifyTokenExists(anyString());
        //Assert
        assertDoesNotThrow(() -> sut.verifyTokenExists(testToken));
    }

    @Test
    void verifyTokenExistsThrowsTokenDoesntExistException() {
        //Arrange
        var expected = new TokenDoesntExistException();
        //Act
        doReturn(false).when(authenticationDAOMock).verifyTokenExists(anyString());
        //Assert
        assertThrows(expected.getClass(), () -> sut.verifyTokenExists(testToken));
    }

    @Test
    void checkIfPlaylistOwnerDoesNotThrowException() {
        //Arrange
        //Act
        doReturn(true).when(authenticationDAOMock).checkIfOwnerOfPlaylist(anyString(), anyInt());
        //Assert
        assertDoesNotThrow(() -> sut.checkIfPlaylistOwner(testToken, testPlaylistId));
    }

    @Test
    void checkIfPlaylistOwnerThrowsNotOwnerOfPlaylistException() {
        //Arrange
        var expected = new NotOwnerOfPlaylistException();
        //Act
        doReturn(false).when(authenticationDAOMock).checkIfOwnerOfPlaylist(anyString(), anyInt());
        //Assert
        assertThrows(expected.getClass(), () -> sut.checkIfPlaylistOwner(testToken, testPlaylistId));
    }

}