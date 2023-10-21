package tech.noetzold.customer;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.annotations.Merge;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.noetzold.model.ProductModel;
import tech.noetzold.service.ProductService;

@ApplicationScoped
public class ProductConsumer {

    @Inject
    ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductConsumer.class);

    @Incoming("products")
    @Merge
    @Blocking
    public ProductModel process(JsonObject incomingProductModelInJson) {

        ProductModel incomingProductModel = incomingProductModelInJson.mapTo(ProductModel.class);

        productService.saveProductModel(incomingProductModel);
        logger.info("Create Product " + incomingProductModel.getProductId() + ".");

        return incomingProductModel;
    }
}
