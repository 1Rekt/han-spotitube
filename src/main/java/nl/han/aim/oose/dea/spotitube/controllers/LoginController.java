package nl.han.aim.oose.dea.spotitube.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.aim.oose.dea.spotitube.datasource.DTOs.LoginRequestDTO;
import nl.han.aim.oose.dea.spotitube.services.LoginService;

@Path("/login")
public class LoginController {
    LoginService loginService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginRequest(LoginRequestDTO loginCredentials) {
        loginService.loginCredentialsCorrect(loginCredentials.getUser(), loginCredentials.getPassword());
        return Response.status(200).entity(loginService.generateLoginResponse(loginCredentials.getUser())).build();
    }

    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

}
