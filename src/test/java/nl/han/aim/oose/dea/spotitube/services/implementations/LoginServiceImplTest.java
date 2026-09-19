package nl.han.aim.oose.dea.spotitube.services.implementations;

import nl.han.aim.oose.dea.spotitube.datasource.DAOs.LoginDAO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.UserDTO;
import nl.han.aim.oose.dea.spotitube.services.exceptions.LoginCredentialsIncorrectException;
import nl.han.aim.oose.dea.spotitube.services.util.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class LoginServiceImplTest {
    private final LoginDAO loginDAOMock = mock(LoginDAO.class);
    private final TokenServiceImpl tokenServiceMock = mock(TokenServiceImpl.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private LoginServiceImpl sut;

    private UserDTO userDTO;
    private final String testUsername = "testUsername";
    private final String testPassword = "1234";

    @BeforeEach
    void setUp() {
        sut = new LoginServiceImpl(loginDAOMock, tokenServiceMock, passwordEncoder);

        userDTO = new UserDTO();
        userDTO.setUser(testUsername);
        userDTO.setToken("testToken");
    }

    @Test
    void loginCredentialsCorrectDoesNotThrowException() {
        //Arrange
        //Act
        doReturn("hashedPassword").when(loginDAOMock).getUserPassword(anyString());
        doReturn(true).when(passwordEncoder).checkPassword(anyString(), anyString());
        //Assert
        assertDoesNotThrow(() -> sut.loginCredentialsCorrect(testUsername, testPassword));
    }

    @Test
    void loginCredentialsCorrectDoesThrowLoginCredentialsIncorrectException() {
        //Arrange
        var expected = new LoginCredentialsIncorrectException();
        //Act
        doReturn("hashedPassword").when(loginDAOMock).getUserPassword(anyString());
        doReturn(false).when(passwordEncoder).checkPassword(anyString(), anyString());
        //Assert
        assertThrows(expected.getClass(), () -> sut.loginCredentialsCorrect(testUsername, testPassword));
    }

    @Test
    void generateLoginResponseReturnsLoginResponse() {
        //Arrange
        var expectedOne = userDTO.getUser();
        var expectedTwo = userDTO.getToken();
        //Act
        doReturn(userDTO.getToken()).when(tokenServiceMock).generateToken();
        doReturn(userDTO.getUser()).when(loginDAOMock).getUserFullname(anyString());
        var actual = sut.generateLoginResponse(userDTO.getUser());
        //Assert
        assertEquals(expectedOne, actual.getUser());
        assertEquals(expectedTwo, actual.getToken());
    }

}