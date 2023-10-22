package tech.noetzold.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;
import tech.noetzold.model.AttributeModel;
import tech.noetzold.service.AttributeService;

import java.util.UUID;

@Path("/api/catalog/v1/attribute")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AttributeController {

    @Inject
    AttributeService attributeService;

    @Channel("attributes")
    Emitter<AttributeModel> quoteRequestEmitter;

    private static final Logger logger = Logger.getLogger(AttributeController.class);

    @GET
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response getAttributeModelById(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);

        AttributeModel attributeModel = attributeService.findAttributeModelById(uuid);

        if(attributeModel.getAttributeId() == null){
            logger.error("There is no attribute with id: " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(attributeModel).build();
    }

    @POST
    @RolesAllowed("admin")
    public Response saveAttributeModel(AttributeModel attributeModel){
        try {
            if (attributeModel.getName() == null) {
                logger.error("Error to create Attribute without name: " + attributeModel.getAttributeId());
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            attributeModel.setAttributeId(null);
            quoteRequestEmitter.send(attributeModel);
            logger.info("Create " + attributeModel.getAttributeId());
            return Response.status(Response.Status.CREATED).entity(attributeModel).build();
        } catch (Exception e) {
            logger.error("Error to create attributeModel: " + attributeModel.getAttributeId());
            e.printStackTrace();
        }
        logger.error("Error to create attributeModel: " + attributeModel.getAttributeId());
        return Response.status(Response.Status.BAD_REQUEST).entity(attributeModel).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response updateAttributeModel(@PathParam("id") String id, AttributeModel updatedAttributeModel) {
        if (id.isBlank() || updatedAttributeModel.getAttributeId() == null) {
            logger.warn("Error to update attributeModel: " + id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        AttributeModel existingAttributeModel = attributeService.findAttributeModelById(UUID.fromString(id));
        if (existingAttributeModel.getAttributeId() == null) {
            logger.warn("Error to update attributeModel: " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        attributeService.updateAttributeModel(updatedAttributeModel);

        return Response.ok(updatedAttributeModel).build();
    }

    @DELETE
    @RolesAllowed("admin")
    public Response deleteAttributeModel(@PathParam("id") String id){
        if (id.isBlank()) {
            logger.warn("Error to delete attributeModel: " + id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        UUID uuid = UUID.fromString(id);

        attributeService.deleteAttributeModelById(uuid);

        return Response.ok().build();
    }

}
