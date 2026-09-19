package nl.han.aim.oose.dea.spotitube.services.implementations;

import jakarta.inject.Inject;
import nl.han.aim.oose.dea.spotitube.datasource.DAOs.LoginDAO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.UserDTO;
import nl.han.aim.oose.dea.spotitube.services.LoginService;
import nl.han.aim.oose.dea.spotitube.services.TokenService;
import nl.han.aim.oose.dea.spotitube.services.exceptions.LoginCredentialsIncorrectException;
import nl.han.aim.oose.dea.spotitube.services.util.PasswordEncoder;

public class LoginServiceImpl implements LoginService {
    LoginDAO loginDAO;
    TokenService tokenService;
    PasswordEncoder passwordEncoder;

    @Inject
    LoginServiceImpl(LoginDAO loginDAO, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.loginDAO = loginDAO;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public void loginCredentialsCorrect(String username, String password) {
        String hashedPassword = loginDAO.getUserPassword(username);
        if (!passwordEncoder.checkPassword(password, hashedPassword)) {
            throw new LoginCredentialsIncorrectException();
        }
    }

    public UserDTO generateLoginResponse(String username) {
        UserDTO loginResponse = new UserDTO();
        String token = tokenService.generateToken();
        loginDAO.giveUserNewToken(username, token);
        String userFullname = loginDAO.getUserFullname(username);
        loginResponse.setUser(userFullname);
        loginResponse.setToken(token);
        return loginResponse;
    }
}
