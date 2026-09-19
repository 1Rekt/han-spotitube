package nl.han.aim.oose.dea.spotitube.controllers.exceptionMappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.han.aim.oose.dea.spotitube.services.exceptions.NotOwnerOfPlaylistException;

@Provider
public class NotOwnerOfPlaylistExceptionMapper implements ExceptionMapper<NotOwnerOfPlaylistException> {
    @Override
    public Response toResponse(NotOwnerOfPlaylistException ex) {
        return Response.status(403).
                entity(ex.getMessage()).
                type("text/plain").
                build();
    }
}
