package nl.han.aim.oose.dea.spotitube.controllers.exceptionMappers;

import nl.han.aim.oose.dea.spotitube.services.exceptions.NotOwnerOfPlaylistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotOwnerOfPlaylistExceptionMapperTest {
    private NotOwnerOfPlaylistExceptionMapper sut;

    @BeforeEach
    void setUp() {
        sut = new NotOwnerOfPlaylistExceptionMapper();
    }

    @Test
    void toResponseReturnsStatusCode403() {
        //Arrange
        var expected = 403;
        //Act
        var actual = sut.toResponse(new NotOwnerOfPlaylistException()).getStatus();
        //Assert
        assertEquals(expected, actual);
    }
}