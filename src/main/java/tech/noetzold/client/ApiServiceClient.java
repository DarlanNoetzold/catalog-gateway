package tech.noetzold.client;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import tech.noetzold.model.AuthResponse;
import tech.noetzold.model.LoginData;

@Path("http://localhost:8091/ecommerce/v1/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ApiServiceClient {

    @POST
    @Path("/authenticate")
    AuthResponse authenticate(LoginData loginData);
}