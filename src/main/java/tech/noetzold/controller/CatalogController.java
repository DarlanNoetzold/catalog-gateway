package tech.noetzold.controller;


import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import tech.noetzold.model.AttributeModel;
import tech.noetzold.service.*;

import java.util.List;
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

    @Channel("attributes")
    Emitter<AttributeModel> quoteRequestEmitterAttribute;

    @GET
    @RolesAllowed("admin")
    public Response getAttributeModelById(){
        for (AttributeModel attributeModel: attributeService.findAllAttributeModel()) {
            quoteRequestEmitterAttribute.send(attributeModel);
        }


        return Response.ok().build();
    }


}
