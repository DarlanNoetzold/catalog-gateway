package tech.noetzold.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;
import tech.noetzold.model.CategoryModel;
import tech.noetzold.service.CategoryService;

import java.util.UUID;

@Path("/api/catalog/v1/category")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryController {

    @Inject
    CategoryService categoryService;

    @Channel("categories")
    Emitter<CategoryModel> quoteRequestEmitter;

    private static final Logger logger = Logger.getLogger(CategoryController.class);

    @GET
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response getCategoryModelById(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);

        CategoryModel categoryModel = categoryService.findCategoryModelById(uuid);

        if(categoryModel.getCategoryId() == null){
            logger.error("There is no category with id: " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(categoryModel).build();
    }

    @POST
    @RolesAllowed("admin")
    public Response saveCategoryModel(CategoryModel categoryModel){
        try {
            if (categoryModel.getName() == null) {
                logger.error("Error to create Category withou name: " + categoryModel.getCategoryId());
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            categoryModel.setCategoryId(null);
            quoteRequestEmitter.send(categoryModel);
            logger.info("Create " + categoryModel.getCategoryId());
            return Response.status(Response.Status.CREATED).entity(categoryModel).build();
        } catch (Exception e) {
            logger.error("Error to create categoryModel: " + categoryModel.getCategoryId());
            e.printStackTrace();
        }
        logger.error("Error to create categoryModel: " + categoryModel.getCategoryId());
        return Response.status(Response.Status.BAD_REQUEST).entity(categoryModel).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response updateCategoryModel(@PathParam("id") String id, CategoryModel updatedCategoryModel) {
        if (id.isBlank()) {
            logger.warn("Error to update categoryModel: " + id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        CategoryModel existingCategoryModel = categoryService.findCategoryModelById(UUID.fromString(id));
        if (existingCategoryModel.getCategoryId() == null) {
            logger.warn("Error to update categoryModel: " + id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        updatedCategoryModel.setCategoryId(existingCategoryModel.getCategoryId());

        categoryService.updateCategoryModel(updatedCategoryModel);

        return Response.ok(updatedCategoryModel).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response deleteCategoryModel(@PathParam("id") String id){
        if (id.isBlank()) {
            logger.warn("Error to delete categoryModel: " + id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        UUID uuid = UUID.fromString(id);

        categoryService.deleteCategoryModelById(uuid);

        return Response.ok().build();
    }
}
