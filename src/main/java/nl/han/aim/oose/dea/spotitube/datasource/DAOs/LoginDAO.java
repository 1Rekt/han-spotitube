package nl.han.aim.oose.dea.spotitube.datasource.DAOs;

public interface LoginDAO {
    String getUserPassword(String username);

    void giveUserNewToken(String username, String token);

    String getUserFullname(String username);
}

