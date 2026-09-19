package nl.han.aim.oose.dea.spotitube.services;

public interface AuthenticationService {
    void verifyTokenExists(String token);

    void checkIfPlaylistOwner(String token, int playlistId);
}
