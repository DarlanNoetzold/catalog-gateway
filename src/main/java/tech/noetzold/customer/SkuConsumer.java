package tech.noetzold.customer;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.annotations.Merge;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.noetzold.model.SkuModel;
import tech.noetzold.service.SkuService;

@ApplicationScoped
public class SkuConsumer {

    @Inject
    SkuService skuService;

    private static final Logger logger = LoggerFactory.getLogger(SkuConsumer.class);

    @Incoming("skus")
    @Merge
    @Blocking
    public SkuModel process(JsonObject incomingSkuModelInJson) {

        SkuModel incomingSkuModel = incomingSkuModelInJson.mapTo(SkuModel.class);

        skuService.saveSkuModel(incomingSkuModel);
        logger.info("Create Sku " + incomingSkuModel.getSkuId() + ".");

        return incomingSkuModel;
    }
}
