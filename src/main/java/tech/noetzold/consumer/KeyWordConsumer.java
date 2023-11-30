package tech.noetzold.consumer;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.annotations.Merge;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.noetzold.model.KeyWordModel;
import tech.noetzold.service.CatalogService;
import tech.noetzold.service.KeyWordService;

@ApplicationScoped
public class KeyWordConsumer {

    @Inject
    KeyWordService keyWordService;

    @Inject
    CatalogService catalogService;

    private static final Logger logger = LoggerFactory.getLogger(KeyWordConsumer.class);

    @Incoming("keywords")
    @Merge
    @Blocking
    public KeyWordModel process(KeyWordModel incomingKeyWordModel) {
        catalogService.sendKeyWord(incomingKeyWordModel);
        keyWordService.saveKeyWordModel(incomingKeyWordModel);
        logger.info("Create KeyWord " + incomingKeyWordModel.getKeyWordId() + ".");

        return incomingKeyWordModel;
    }
}
