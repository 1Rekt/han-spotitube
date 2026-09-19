package nl.han.aim.oose.dea.spotitube.datasource.DAOs.implementations;

import nl.han.aim.oose.dea.spotitube.datasource.util.DatabaseProperties;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.sql.*;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class LoginDAOImplTest {
    private final DatabaseProperties databaseProperties = new DatabaseProperties();
    private LoginDAOImpl sut;

    private final String testUsername = "Gino";
    private final String testFullname = "Gino Janssen";
    private final String testPassword = "$2a$10$Zr6VBqeOK2eRsKs8pzJeteGWnG9YljKx/1WoulIKmPaSOvxPt25g.";

    @BeforeEach
    void setUp() throws SQLException {
        sut = new LoginDAOImpl(databaseProperties);

        loadTestDatabase("testDB.sql");
    }

    private void loadTestDatabase(String filename) throws SQLException {
        RunScript.execute(DriverManager.getConnection(databaseProperties.connectionString()), new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("/" + filename))));
    }

    @Test
    void getUserPasswordReturnsCorrectPassword() {
        var expected = testPassword;
        //Arrange
        //Act
        var actual = sut.getUserPassword(testUsername);
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void giveUserNewTokenGivesUserANewToken() {
        //Arrange
        var oldToken = "";
        var newToken = "";
        //Act
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT token FROM user WHERE username = ?");
            statement.setString(1, testUsername);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                oldToken = resultSet.getString("token");
            }
            sut.giveUserNewToken(testUsername, UUID.randomUUID().toString());
            statement = connection.prepareStatement("SELECT token FROM user WHERE username = ?");
            statement.setString(1, testUsername);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                newToken = resultSet.getString("token");
            }
            statement.close();
            connection.close();
        } catch (SQLException ignored) {
        }
        //Assert
        assertNotEquals(oldToken, newToken);
    }

    @Test
    void getUserFullnameReturnsCorrectFullname() {
        var expected = testFullname;
        //Arrange
        //Act
        var actual = sut.getUserFullname(testUsername);
        //Assert
        assertEquals(expected, actual);
    }

}