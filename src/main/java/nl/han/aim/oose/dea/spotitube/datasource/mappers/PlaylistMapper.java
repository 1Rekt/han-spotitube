package nl.han.aim.oose.dea.spotitube.datasource.mappers;

import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistMapper {

    public PlaylistDTO mapPlaylist(ResultSet resultSet, String token) throws SQLException {
        PlaylistDTO playlist = new PlaylistDTO();
        playlist.setId(resultSet.getInt("id"));
        playlist.setName(resultSet.getString("name"));
        playlist.setOwner(resultSet.getString("token").equals(token));
        playlist.setTracks(new ArrayList<>());
        return playlist;
    }

}
