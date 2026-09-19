package nl.han.aim.oose.dea.spotitube.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.PlaylistDTO;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.TrackDTO;
import nl.han.aim.oose.dea.spotitube.services.AuthenticationService;
import nl.han.aim.oose.dea.spotitube.services.PlaylistService;
import nl.han.aim.oose.dea.spotitube.services.TrackService;

@Path("/playlists")
public class PlaylistController {
    AuthenticationService authenticationService;
    PlaylistService playlistService;
    TrackService trackService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token) {
        authenticationService.verifyTokenExists(token);
        return Response.status(200).entity(playlistService.getAllPlaylists(token)).build();
    }

    @GET
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksForPlaylist(@QueryParam("token") String token, @PathParam("id") int playlistId) {
        authenticationService.verifyTokenExists(token);
        return Response.status(200).entity(trackService.getAllTracksForPlaylist(playlistId)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@QueryParam("token") String token, @PathParam("id") int playlistId, PlaylistDTO playlist) {
        authenticationService.verifyTokenExists(token);
        authenticationService.checkIfPlaylistOwner(token, playlistId);
        playlistService.editPlaylist(playlist);
        return Response.status(201).entity(playlistService.getAllPlaylists(token)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO playlist) {
        authenticationService.verifyTokenExists(token);
        playlistService.addPlaylist(token, playlist.getName());
        return Response.status(201).entity(playlistService.getAllPlaylists(token)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@QueryParam("token") String token, @PathParam("id") int playlistId) {
        authenticationService.verifyTokenExists(token);
        authenticationService.checkIfPlaylistOwner(token, playlistId);
        playlistService.deletePlaylist(playlistId);
        return Response.status(200).entity(playlistService.getAllPlaylists(token)).build();
    }

    @POST
    @Path("/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@QueryParam("token") String token, @PathParam("id") int playlistId, TrackDTO track) {
        authenticationService.verifyTokenExists(token);
        authenticationService.checkIfPlaylistOwner(token, playlistId);
        playlistService.addTrackToPlaylist(playlistId, track.getId(), track.isOfflineAvailable());
        return Response.status(201).entity(playlistService.getAllPlaylists(token)).build();
    }

    @DELETE
    @Path("/{playlistId}/tracks/{trackId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrackFromPlaylist(@QueryParam("token") String token, @PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId) {
        authenticationService.verifyTokenExists(token);
        authenticationService.checkIfPlaylistOwner(token, playlistId);
        playlistService.deleteTrackFromPlaylist(playlistId, trackId);
        return Response.status(200).entity(playlistService.getAllPlaylists(token)).build();
    }

    @Inject
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Inject
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

}
