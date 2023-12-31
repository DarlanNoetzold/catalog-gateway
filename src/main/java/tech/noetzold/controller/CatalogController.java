package tech.noetzold.controller;


import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.noetzold.consumer.SkuConsumer;
import tech.noetzold.model.*;
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

    @Channel("categories")
    Emitter<CategoryModel> quoteRequestEmitterCategory;

    @Channel("keywords")
    Emitter<KeyWordModel> quoteRequestEmitterKeyWord;

    @Channel("medias")
    Emitter<MediaModel> quoteRequestEmitterMedia;

    @Channel("products")
    Emitter<ProductModel> quoteRequestEmitterProduct;

    @Channel("skus")
    Emitter<SkuModel> quoteRequestEmitterSku;

    private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);

    @GET
    @RolesAllowed("admin")
    public Response indexing(){

        logger.info("Init Indexing");

        for (AttributeModel attributeModel: attributeService.findAllAttributeModel()) {
            quoteRequestEmitterAttribute.send(attributeModel);
        }

        for (CategoryModel categoryModel: categoryService.findAllCategoryModel()) {
            quoteRequestEmitterCategory.send(categoryModel);
        }

        for (KeyWordModel keyWordModel: keyWordService.findAllAKeyWordModel()) {
            quoteRequestEmitterKeyWord.send(keyWordModel);
        }

        for (MediaModel mediaModel: mediaService.findAllMediaModel()) {
            quoteRequestEmitterMedia.send(mediaModel);
        }

        for (ProductModel productModel: productService.findAllProductModel()) {
            quoteRequestEmitterProduct.send(productModel);
        }

        for (SkuModel skuModel: skuService.findAllSkuModel()) {
            quoteRequestEmitterSku.send(skuModel);
        }

        logger.info("Finish Indexing");

        return Response.ok().build();
    }


}
