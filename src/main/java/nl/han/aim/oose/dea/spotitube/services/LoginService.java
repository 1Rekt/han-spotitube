package nl.han.aim.oose.dea.spotitube.services;

import nl.han.aim.oose.dea.spotitube.datasource.DTOs.UserDTO;

public interface LoginService {
    public void loginCredentialsCorrect(String username, String password);

    public UserDTO generateLoginResponse(String username);
}
