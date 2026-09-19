package nl.han.aim.oose.dea.spotitube.datasource.DAOs.implementations;

import jakarta.inject.Inject;
import nl.han.aim.oose.dea.spotitube.datasource.DAOs.LoginDAO;
import nl.han.aim.oose.dea.spotitube.datasource.util.DatabaseProperties;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginDAOImpl implements LoginDAO {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final DatabaseProperties databaseProperties;

    @Inject
    public LoginDAOImpl(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public String getUserPassword(String username) {
        String hashedPassword = "";
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT password FROM user WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                hashedPassword = resultSet.getString("password");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return hashedPassword;
    }

    public void giveUserNewToken(String username, String token) {
        try (Connection connection = DriverManager.getConnection(databaseProperties.connectionString())) {
            PreparedStatement statement = connection.prepareStatement("UPDATE user SET token = ? WHERE username = ?");
            statement.setString(1, token);
            statement.setString(2, username);
            statement.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Can't insert token for user with username " + username, e);
        }
    }

    public String getUserFullname(String username) {
        String fullname = "";
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT full_name FROM user WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                fullname = resultSet.getString("full_name");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return fullname;
    }

}
