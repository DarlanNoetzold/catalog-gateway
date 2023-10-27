package tech.noetzold.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;
import tech.noetzold.model.ProductModel;
import tech.noetzold.service.ProductService;

import java.util.List;
import java.util.UUID;

@Path("/api/catalog/v1/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {

    @Inject
    ProductService productService;

    @Channel("products")
    Emitter<ProductModel> quoteRequestEmitter;

    private static final Logger logger = Logger.getLogger(ProductController.class);

    @GET
    @RolesAllowed("admin")
    public Response getAll(@QueryParam("page") int page, @QueryParam("size") int size, @QueryParam("sortBy") String sortBy) {
        if (page <= 0 || size <= 0) {
            logger.error("Invalid page: " + page +" or size: "+ size);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<ProductModel> productModels = productService.findAll(page, size,sortBy);
        logger.info("Returned ProductModel quantity: " + productModels.size());
        return Response.ok(productModels).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response getProductModelById(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);

        ProductModel productModel = productService.findProductModelById(uuid);

        if(productModel.getProductId() == null){
            logger.error("There is no product with id: " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(productModel).build();
    }

    @POST
    @RolesAllowed("admin")
    public Response saveProductModel(ProductModel productModel){
        try {
            if (productModel.getDescription() == null) {
                logger.error("Error to create Product without description: " + productModel.getProductId());
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            productModel.setProductId(null);
            quoteRequestEmitter.send(productModel);
            logger.info("Create " + productModel.getProductId());
            return Response.status(Response.Status.CREATED).entity(productModel).build();
        } catch (Exception e) {
            logger.error("Error to create productModel: " + productModel.getProductId());
            e.printStackTrace();
        }
        logger.error("Error to create productModel: " + productModel.getProductId());
        return Response.status(Response.Status.BAD_REQUEST).entity(productModel).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response updateProductModel(@PathParam("id") String id, ProductModel updatedProductModel) {
        if (id.isBlank()) {
            logger.warn("Error to update productModel: " + id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        ProductModel existingProductModel = productService.findProductModelById(UUID.fromString(id));
        if (existingProductModel.getProductId() == null) {
            logger.warn("Error to update productModel: " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        updatedProductModel.setProductId(existingProductModel.getProductId());

        productService.updateProductModel(updatedProductModel);

        return Response.ok(updatedProductModel).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response deleteProductModel(@PathParam("id") String id){
        if (id.isBlank()) {
            logger.warn("Error to delete productModel: " + id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        UUID uuid = UUID.fromString(id);

        productService.deleteProductModelById(uuid);

        return Response.ok().build();
    }
}
