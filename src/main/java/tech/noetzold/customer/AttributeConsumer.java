package tech.noetzold.customer;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.annotations.Merge;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.noetzold.model.AttributeModel;
import tech.noetzold.service.AttributeService;

@ApplicationScoped
public class AttributeConsumer {

    @Inject
    AttributeService attributeService;

    private static final Logger logger = LoggerFactory.getLogger(AttributeConsumer.class);

    @Incoming("attributes")
    @Merge
    @Blocking
    public AttributeModel process(JsonObject incomingAttributeModelInJson) {

        AttributeModel incomingAttributeModel = incomingAttributeModelInJson.mapTo(AttributeModel.class);

        attributeService.saveAttributeModel(incomingAttributeModel);
        logger.info("Create Attribute " + incomingAttributeModel.getAttributeId() + ".");

        return incomingAttributeModel;
    }
}
