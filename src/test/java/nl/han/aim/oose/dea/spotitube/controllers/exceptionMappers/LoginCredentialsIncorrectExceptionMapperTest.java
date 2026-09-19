package nl.han.aim.oose.dea.spotitube.controllers.exceptionMappers;

import nl.han.aim.oose.dea.spotitube.services.exceptions.LoginCredentialsIncorrectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginCredentialsIncorrectExceptionMapperTest {
    private LoginCredentialsIncorrectExceptionMapper sut;

    @BeforeEach
    void setUp() {
        sut = new LoginCredentialsIncorrectExceptionMapper();
    }

    @Test
    void toResponseReturnsStatusCode403() {
        //Arrange
        var expected = 403;
        //Act
        var actual = sut.toResponse(new LoginCredentialsIncorrectException()).getStatus();
        //Assert
        assertEquals(expected, actual);
    }
}