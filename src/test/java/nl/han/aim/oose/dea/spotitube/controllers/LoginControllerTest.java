package nl.han.aim.oose.dea.spotitube.controllers;

import nl.han.aim.oose.dea.spotitube.datasource.DTOs.LoginRequestDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.UserDTO;
import nl.han.aim.oose.dea.spotitube.services.LoginService;
import nl.han.aim.oose.dea.spotitube.services.exceptions.LoginCredentialsIncorrectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
    private final LoginService loginServiceMock = mock(LoginService.class);
    private LoginController sut;
    private LoginRequestDTO loginRequestDTO;
    @Captor
    private ArgumentCaptor<String> strOneCaptor;
    @Captor
    private ArgumentCaptor<String> strTwoCaptor;

    @BeforeEach
    public void setup() {
        sut = new LoginController();
        sut.setLoginService(loginServiceMock);

        loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUser("testUser");
        loginRequestDTO.setPassword("testPassword");
    }

    @Test
    void loginRequestReturnsStatusCode200() {
        //Arrange
        var expected = 200;
        //Act
        doNothing().when(loginServiceMock).loginCredentialsCorrect(anyString(), anyString());
        var actual = sut.loginRequest(loginRequestDTO).getStatus();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void loginRequestReturnsCorrectEntity() {
        //Arrange
        UserDTO expected = new UserDTO();
        expected.setUser("testUser");
        expected.setToken("testToken");
        //Act
        doNothing().when(loginServiceMock).loginCredentialsCorrect(anyString(), anyString());
        doReturn(expected).when(loginServiceMock).generateLoginResponse(anyString());
        var actual = sut.loginRequest(loginRequestDTO).getEntity();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void loginRequestReturnsStatusCode403() {
        //Arrange
        var expected = new LoginCredentialsIncorrectException();
        //Act
        doThrow(new LoginCredentialsIncorrectException()).when(loginServiceMock).loginCredentialsCorrect(anyString(), anyString());
        //Assert
        assertThrows(expected.getClass(), () -> sut.loginRequest(loginRequestDTO));
    }

    @Test
    void loginRequestPassesCorrectArgumentsToLoginCredentialsCorrect() {
        //Arrange
        var expectedOne = "testUser";
        var expectedTwo = "testPassword";
        //Act
        sut.loginRequest(loginRequestDTO);
        verify(loginServiceMock).loginCredentialsCorrect(strOneCaptor.capture(), strTwoCaptor.capture());
        var actualOne = strOneCaptor.getValue();
        var actualTwo = strTwoCaptor.getValue();
        //Assert
        assertEquals(expectedOne, actualOne);
        assertEquals(expectedTwo, actualTwo);
    }

    @Test
    void loginRequestPassesCorrectArgumentsToGenerateLoginResponse() {
        //Arrange
        var expected = "testUser";
        //Act
        sut.loginRequest(loginRequestDTO);
        verify(loginServiceMock).generateLoginResponse(strOneCaptor.capture());
        var actual = strOneCaptor.getValue();
        //Assert
        assertEquals(expected, actual);
    }

}
