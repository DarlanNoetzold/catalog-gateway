package tech.noetzold.controller;


import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tech.noetzold.model.AttributeModel;
import tech.noetzold.service.*;

import java.util.UUID;

@Path("/api/catalog/sendUpdateNotification")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CatalogController {

    @Inject
    AttributeService attributeService;

    @Inject
    CatalogService catalogService;

    @Inject
    CategoryService categoryService;

    @Inject
    KeyWordService keyWordService;

    @Inject
    MediaService mediaService;

    @Inject
    ProductService productService;

    @Inject
    SkuService skuService;

    @GET
    @RolesAllowed("admin")
    public Response getAttributeModelById(){
        attributeService.findAllAttributeModel();

        return Response.ok(attributeModel).build();
    }


}
