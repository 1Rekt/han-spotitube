package nl.han.aim.oose.dea.spotitube.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.aim.oose.dea.spotitube.services.AuthenticationService;
import nl.han.aim.oose.dea.spotitube.services.TrackService;

@Path("/tracks")
public class TrackController {
    AuthenticationService authenticationService;
    TrackService trackService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAvailableTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int playlistId) {
        authenticationService.verifyTokenExists(token);
        authenticationService.checkIfPlaylistOwner(token, playlistId);
        return Response.status(200).entity(trackService.getAllAvailableTracks(playlistId)).build();
    }

    @Inject
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Inject
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

}
