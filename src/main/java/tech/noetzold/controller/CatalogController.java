package tech.noetzold.controller;


import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import tech.noetzold.service.*;

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

    
}
