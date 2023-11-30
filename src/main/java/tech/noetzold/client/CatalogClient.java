package tech.noetzold.client;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import tech.noetzold.model.SkuModel;

@Path("/ecommerce/v1/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CatalogClient {

    @POST
    @Path("/save")
    SkuModel saveSku(@HeaderParam("Authorization") String token, SkuModel skuModel);
}
