package tech.noetzold.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.hibernate.Hibernate;
import org.jboss.logging.Logger;
import tech.noetzold.model.ProductModel;
import tech.noetzold.model.SkuModel;
import tech.noetzold.service.ProductService;
import tech.noetzold.service.SkuService;

import java.util.List;
import java.util.UUID;

@Path("/api/catalog/v1/sku")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SkuController {

    @Inject
    SkuService skuService;

    @Inject
    ProductService productService;

    @Channel("skus")
    Emitter<SkuModel> quoteRequestEmitter;

    private static final Logger logger = Logger.getLogger(SkuController.class);

    @GET
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response getSkuModelById(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);

        SkuModel skuModel = skuService.findSkuModelById(uuid);

        if(skuModel.getSkuId() == null){
            logger.error("There is no sku with id: " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(skuModel).build();
    }

    @GET
    @Path("/getSkus/{id}")
    @RolesAllowed("admin")
    public Response getAllSkuByProductId(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);

        ProductModel productModel = productService.findProductModelById(uuid);

        List<SkuModel> listSkuModel = skuService.findSkuModelByProductId(productModel);

        if(listSkuModel.isEmpty()){
            logger.error("There is no sku with product id: " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(listSkuModel).build();
    }

    @POST
    @RolesAllowed("admin")
    public Response saveSkuModel(SkuModel skuModel){
        try {
            if (skuModel.getDisplayName() == null) {
                logger.error("Error to create Sku without name: " + skuModel.getSkuId());
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            skuModel.setSkuId(null);
            quoteRequestEmitter.send(skuModel);
            logger.info("Create " + skuModel.getSkuId());
            return Response.status(Response.Status.CREATED).entity(skuModel).build();
        } catch (Exception e) {
            logger.error("Error to create skuModel: " + skuModel.getSkuId());
            e.printStackTrace();
        }
        logger.error("Error to create skuModel: " + skuModel.getSkuId());
        return Response.status(Response.Status.BAD_REQUEST).entity(skuModel).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response updateSkuModel(@PathParam("id") String id, SkuModel updatedSkuModel) {
        if (id.isBlank()) {
            logger.warn("Error to update skuModel: " + id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        SkuModel existingSkuModel = skuService.findSkuModelById(UUID.fromString(id));
        if (existingSkuModel.getSkuId() == null) {
            logger.warn("Error to update skuModel: " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        updatedSkuModel.setSkuId(existingSkuModel.getSkuId());

        skuService.updateSkuModel(updatedSkuModel);

        return Response.ok(updatedSkuModel).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response deleteSkuModel(@PathParam("id") String id){
        if (id.isBlank()) {
            logger.warn("Error to delete skuModel: " + id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        UUID uuid = UUID.fromString(id);

        skuService.deleteSkuModelById(uuid);

        return Response.ok().build();
    }
    
}
