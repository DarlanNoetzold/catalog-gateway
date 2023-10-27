package tech.noetzold.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;
import tech.noetzold.model.KeyWordModel;
import tech.noetzold.service.KeyWordService;

import java.util.UUID;

@Path("/api/catalog/v1/keyword")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KeyWordController {

    @Inject
    KeyWordService keywordService;

    @Channel("keywords")
    Emitter<KeyWordModel> quoteRequestEmitter;

    private static final Logger logger = Logger.getLogger(KeyWordController.class);

    @GET
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response getKeyWordModelById(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);

        KeyWordModel keywordModel = keywordService.findKeyWordModelById(uuid);

        if(keywordModel.getKeyWordId() == null){
            logger.error("There is no keyword with id: " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(keywordModel).build();
    }

    @POST
    @RolesAllowed("admin")
    public Response saveKeyWordModel(KeyWordModel keywordModel){
        try {
            if (keywordModel.getKeyWord() == null) {
                logger.error("Error to create KeyWord without keyword: " + keywordModel.getKeyWordId());
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            keywordModel.setKeyWordId(null);
            quoteRequestEmitter.send(keywordModel);
            logger.info("Create " + keywordModel.getKeyWordId());
            return Response.status(Response.Status.CREATED).entity(keywordModel).build();
        } catch (Exception e) {
            logger.error("Error to create keywordModel: " + keywordModel.getKeyWordId());
            e.printStackTrace();
        }
        logger.error("Error to create keywordModel: " + keywordModel.getKeyWordId());
        return Response.status(Response.Status.BAD_REQUEST).entity(keywordModel).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response updateKeyWordModel(@PathParam("id") String id, KeyWordModel updatedKeyWordModel) {
        if (id.isBlank()) {
            logger.warn("Error to update keywordModel: " + id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        KeyWordModel existingKeyWordModel = keywordService.findKeyWordModelById(UUID.fromString(id));
        if (existingKeyWordModel.getKeyWordId() == null) {
            logger.warn("Error to update keywordModel: " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        updatedKeyWordModel.setKeyWordId(existingKeyWordModel.getKeyWordId());

        keywordService.updateKeyWordModel(updatedKeyWordModel);

        return Response.ok(updatedKeyWordModel).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response deleteKeyWordModel(@PathParam("id") String id){
        if (id.isBlank()) {
            logger.warn("Error to delete keywordModel: " + id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        UUID uuid = UUID.fromString(id);

        keywordService.deleteKeyWordModelById(uuid);

        return Response.ok().build();
    }
}
