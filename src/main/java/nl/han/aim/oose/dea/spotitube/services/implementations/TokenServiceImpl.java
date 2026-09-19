package nl.han.aim.oose.dea.spotitube.services.implementations;

import jakarta.inject.Inject;
import nl.han.aim.oose.dea.spotitube.datasource.DAOs.AuthenticationDAO;
import nl.han.aim.oose.dea.spotitube.services.TokenService;

import java.util.UUID;

public class TokenServiceImpl implements TokenService {
    AuthenticationDAO authenticationDAO;

    @Inject
    TokenServiceImpl(AuthenticationDAO authenticationDAO) {
        this.authenticationDAO = authenticationDAO;
    }

    public String generateToken() {
        String token = UUID.randomUUID().toString();
        while (authenticationDAO.verifyTokenExists(token)) {
            token = UUID.randomUUID().toString();
        }
        return token;
    }
}
