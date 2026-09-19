package nl.han.aim.oose.dea.spotitube.datasource.DAOs.implementations;

import jakarta.inject.Inject;
import nl.han.aim.oose.dea.spotitube.datasource.DAOs.AuthenticationDAO;
import nl.han.aim.oose.dea.spotitube.datasource.util.DatabaseProperties;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthenticationDAOImpl implements AuthenticationDAO {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final DatabaseProperties databaseProperties;

    @Inject
    public AuthenticationDAOImpl(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public boolean verifyTokenExists(String token) {
        boolean tokenExists = false;
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT 1 FROM user WHERE token = ?");
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tokenExists = true;
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return tokenExists;
    }

    public boolean checkIfOwnerOfPlaylist(String token, int playlistId) {
        boolean isOwner = false;
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT 1 FROM playlist INNER JOIN user ON owner = username WHERE token = ? AND id = ?");
            statement.setString(1, token);
            statement.setInt(2, playlistId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                isOwner = true;
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return isOwner;
    }

}
