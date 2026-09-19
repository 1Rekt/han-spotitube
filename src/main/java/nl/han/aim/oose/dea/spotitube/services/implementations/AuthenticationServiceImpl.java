package nl.han.aim.oose.dea.spotitube.services.implementations;

import jakarta.inject.Inject;
import nl.han.aim.oose.dea.spotitube.datasource.DAOs.AuthenticationDAO;
import nl.han.aim.oose.dea.spotitube.services.AuthenticationService;
import nl.han.aim.oose.dea.spotitube.services.exceptions.NotOwnerOfPlaylistException;
import nl.han.aim.oose.dea.spotitube.services.exceptions.TokenDoesntExistException;

public class AuthenticationServiceImpl implements AuthenticationService {
    AuthenticationDAO authenticationDAO;

    @Inject
    AuthenticationServiceImpl(AuthenticationDAO authenticationDAO) {
        this.authenticationDAO = authenticationDAO;
    }

    public void verifyTokenExists(String token) {
        if (!authenticationDAO.verifyTokenExists(token)) {
            throw new TokenDoesntExistException();
        }
    }

    public void checkIfPlaylistOwner(String token, int playlistId) {
        if (!authenticationDAO.checkIfOwnerOfPlaylist(token, playlistId)) {
            throw new NotOwnerOfPlaylistException();
        }
    }

}
