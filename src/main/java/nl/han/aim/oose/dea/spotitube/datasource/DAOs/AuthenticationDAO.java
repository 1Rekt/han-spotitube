package nl.han.aim.oose.dea.spotitube.datasource.DAOs;

public interface AuthenticationDAO {
    boolean verifyTokenExists(String token);

    boolean checkIfOwnerOfPlaylist(String token, int playlistId);
}
