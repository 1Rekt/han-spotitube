package nl.han.aim.oose.dea.spotitube.controllers.exceptionMappers;

import nl.han.aim.oose.dea.spotitube.services.exceptions.TokenDoesntExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TokenDoesntExistExceptionMapperTest {
    private TokenDoesntExistExceptionMapper sut;

    @BeforeEach
    void setUp() {
        sut = new TokenDoesntExistExceptionMapper();
    }

    @Test
    void toResponseReturnsStatusCode401() {
        //Arrange
        var expected = 401;
        //Act
        var actual = sut.toResponse(new TokenDoesntExistException()).getStatus();
        //Assert
        assertEquals(expected, actual);
    }

}