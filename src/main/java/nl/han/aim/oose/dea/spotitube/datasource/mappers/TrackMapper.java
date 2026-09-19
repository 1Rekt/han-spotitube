package nl.han.aim.oose.dea.spotitube.datasource.mappers;

import nl.han.aim.oose.dea.spotitube.datasource.DTOs.TrackDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackMapper {

    public TrackDTO mapTrack(ResultSet resultSet) throws SQLException {
        TrackDTO track = new TrackDTO();
        track.setId(resultSet.getInt("id"));
        track.setTitle(resultSet.getString("title"));
        track.setPerformer(resultSet.getString("performer"));
        track.setDuration(resultSet.getInt("duration"));
        track.setAlbum(resultSet.getString("album"));
        track.setPlaycount(resultSet.getInt("playcount"));
        track.setPublicationDate(resultSet.getString("publication_date"));
        track.setDescription(resultSet.getString("description"));
        track.setOfflineAvailable(resultSet.getBoolean("offline_available"));
        return track;
    }

}
