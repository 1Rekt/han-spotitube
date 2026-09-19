package nl.han.aim.oose.dea.spotitube.controllers.exceptionMappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.han.aim.oose.dea.spotitube.services.exceptions.LoginCredentialsIncorrectException;

@Provider
public class LoginCredentialsIncorrectExceptionMapper implements ExceptionMapper<LoginCredentialsIncorrectException> {
    @Override
    public Response toResponse(LoginCredentialsIncorrectException ex) {
        return Response.status(403).
                entity(ex.getMessage()).
                type("text/plain").
                build();
    }
}
