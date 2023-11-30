package tech.noetzold.client;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import tech.noetzold.model.*;

@Path("http://localhost:8091/ecommerce/v1/catalog/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CatalogClient {

    @POST
    @Path("/sku")
    SkuModel sendSku(@HeaderParam("Authorization") String token, SkuModel skuModel);

    @POST
    @Path("/product")
    SkuModel sendProduct(@HeaderParam("Authorization") String token, ProductModel productModel);

    @POST
    @Path("/media")
    SkuModel sendMedia(@HeaderParam("Authorization") String token, MediaModel mediaModel);

    @POST
    @Path("/keyword")
    SkuModel sendKeyWord(@HeaderParam("Authorization") String token, KeyWordModel keyWordModel);

    @POST
    @Path("/category")
    SkuModel sendCategory(@HeaderParam("Authorization") String token, CategoryModel categoryModel);

    @POST
    @Path("/attribute")
    SkuModel sendAttribute(@HeaderParam("Authorization") String token, AttributeModel attributeModel);
}
