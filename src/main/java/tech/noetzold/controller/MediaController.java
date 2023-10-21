package tech.noetzold.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;
import tech.noetzold.model.MediaModel;
import tech.noetzold.service.MediaService;

import java.util.UUID;

@Path("/api/catalog/v1/media")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MediaController {

    @Inject
    MediaService mediaService;

    @Channel("medias")
    Emitter<MediaModel> quoteRequestEmitter;

    private static final Logger logger = Logger.getLogger(MediaController.class);

    @GET
    @Path("/paymentId/{id}")
    @RolesAllowed("admin")
    public Response getMediaModelById(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);

        MediaModel mediaModel = mediaService.findMediaModelById(uuid);

        if(mediaModel.getMediaId() == null){
            logger.error("There is no media with id: " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(mediaModel).build();
    }

    @POST
    @RolesAllowed("admin")
    public Response saveMediaModel(MediaModel mediaModel){
        try {
            if (mediaModel.getSku() == null) {
                logger.error("Error to create Media without sku: " + mediaModel.getMediaId());
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            mediaModel.setMediaId(null);
            quoteRequestEmitter.send(mediaModel);
            logger.info("Create " + mediaModel.getMediaId());
            return Response.status(Response.Status.CREATED).entity(mediaModel).build();
        } catch (Exception e) {
            logger.error("Error to create mediaModel: " + mediaModel.getMediaId());
            e.printStackTrace();
        }
        logger.error("Error to create mediaModel: " + mediaModel.getMediaId());
        return Response.status(Response.Status.BAD_REQUEST).entity(mediaModel).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response updateMediaModel(@PathParam("id") String id, MediaModel updatedMediaModel) {
        if (id.isBlank() || updatedMediaModel.getMediaId() == null) {
            logger.warn("Error to update mediaModel: " + id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        MediaModel existingMediaModel = mediaService.findMediaModelById(UUID.fromString(id));
        if (existingMediaModel.getMediaId() == null) {
            logger.warn("Error to update mediaModel: " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        mediaService.updateMediaModel(updatedMediaModel);

        return Response.ok(updatedMediaModel).build();
    }

    @DELETE
    @RolesAllowed("admin")
    public Response deleteMediaModel(@PathParam("id") String id){
        if (id.isBlank()) {
            logger.warn("Error to delete mediaModel: " + id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        UUID uuid = UUID.fromString(id);

        mediaService.deleteMediaModelById(uuid);

        return Response.ok().build();
    }
}
