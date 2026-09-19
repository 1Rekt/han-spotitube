package nl.han.aim.oose.dea.spotitube.datasource.DAOs.implementations;

import nl.han.aim.oose.dea.spotitube.datasource.util.DatabaseProperties;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthenticationDAOImplTest {
    private final DatabaseProperties databaseProperties = new DatabaseProperties();
    private AuthenticationDAOImpl sut;

    private final String testToken = "b34797ea-7977-40dd-af91-794397843e65";
    private final int testPlaylistId = 1;

    @BeforeEach
    void setUp() throws SQLException {
        sut = new AuthenticationDAOImpl(databaseProperties);

        loadTestDatabase("testDB.sql");
    }

    private void loadTestDatabase(String filename) throws SQLException {
        RunScript.execute(DriverManager.getConnection(databaseProperties.connectionString()), new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("/" + filename))));
    }

    @Test
    void verifyTokenChecksACorrectToken() {
        //Arrange
        //Act
        var actual = sut.verifyTokenExists(testToken);
        //Assert
        assertTrue(actual);
    }

    @Test
    void verifyTokenChecksABadToken() {
        //Arrange
        //Act
        var actual = sut.verifyTokenExists("1234");
        //Assert
        assertFalse(actual);
    }

    @Test
    void checkIfOwnerOfPlaylistChecksARealOwner() {
        //Arrange
        //Act
        var actual = sut.checkIfOwnerOfPlaylist(testToken, testPlaylistId);
        //Assert
        assertTrue(actual);
    }

    @Test
    void checkIfOwnerOfPlaylistChecksANotOwner() {
        //Arrange
        //Act
        var actual = sut.checkIfOwnerOfPlaylist(testToken, 2);
        //Assert
        assertFalse(actual);
    }

}