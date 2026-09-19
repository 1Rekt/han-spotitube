package nl.han.aim.oose.dea.spotitube.services.implementations;

import nl.han.aim.oose.dea.spotitube.datasource.DAOs.AuthenticationDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

class TokenServiceImplTest {
    private final AuthenticationDAO authenticationDAOMock = mock(AuthenticationDAO.class);
    private TokenServiceImpl sut;

    private final String testToken = "testToken";
    private MockedStatic<UUID> uuidMockedStatic;

    @BeforeEach
    void setUp() {
        sut = new TokenServiceImpl(authenticationDAOMock);

        uuidMockedStatic = mockStatic(UUID.class);
    }

    @AfterEach
    public void cleanup() {
        uuidMockedStatic.close();
    }

    @Test
    void generateTokenReturnsCorrectUUID() {
        //Arrange
        var expected = new UUID(1, 1).toString();
        //Act
        uuidMockedStatic.when(UUID::randomUUID).thenReturn(new UUID(1, 1));
        doReturn(false).when(authenticationDAOMock).verifyTokenExists(testToken);
        var actual = sut.generateToken();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void generateTokenReturnsNewUUIDWhenAlreadyExists() {
        //Arrange
        var expected = new UUID(2, 2).toString();
        var notExpected = new UUID(1, 1).toString();
        //Act
        uuidMockedStatic.when(UUID::randomUUID).thenReturn(new UUID(1, 1));
        doReturn(true).when(authenticationDAOMock).verifyTokenExists(testToken);
        uuidMockedStatic.when(UUID::randomUUID).thenReturn(new UUID(2, 2));
        var actual = sut.generateToken();
        //Assert
        assertNotEquals(notExpected, actual);
        assertEquals(expected, actual);
    }

}