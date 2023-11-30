package tech.noetzold.consumer;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.annotations.Merge;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.noetzold.model.AttributeModel;
import tech.noetzold.service.AttributeService;
import tech.noetzold.service.CatalogService;

@ApplicationScoped
public class AttributeConsumer {

    @Inject
    AttributeService attributeService;

    @Inject
    CatalogService catalogService;

    private static final Logger logger = LoggerFactory.getLogger(AttributeConsumer.class);

    @Incoming("attributes")
    @Merge
    @Blocking
    public AttributeModel process(AttributeModel incomingAttributeModel) {
        catalogService.sendAttribute(incomingAttributeModel);
        attributeService.saveAttributeModel(incomingAttributeModel);
        logger.info("Create Attribute " + incomingAttributeModel.getAttributeId() + ".");

        return incomingAttributeModel;
    }
}
