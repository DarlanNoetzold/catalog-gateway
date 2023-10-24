package tech.noetzold.consumer;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.annotations.Merge;
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
    public ProductModel process(ProductModel incomingProductModel) {

        productService.saveProductModel(incomingProductModel);
        logger.info("Create Product " + incomingProductModel.getProductId() + ".");

        return incomingProductModel;
    }
}
