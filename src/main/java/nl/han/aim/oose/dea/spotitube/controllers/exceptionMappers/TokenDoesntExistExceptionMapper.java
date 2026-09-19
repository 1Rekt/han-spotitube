package nl.han.aim.oose.dea.spotitube.controllers.exceptionMappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.han.aim.oose.dea.spotitube.services.exceptions.TokenDoesntExistException;

@Provider
public class TokenDoesntExistExceptionMapper implements ExceptionMapper<TokenDoesntExistException> {
    @Override
    public Response toResponse(TokenDoesntExistException ex) {
        return Response.status(401).
                entity(ex.getMessage()).
                type("text/plain").
                build();
    }
}
