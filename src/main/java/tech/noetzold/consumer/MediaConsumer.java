package tech.noetzold.consumer;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.annotations.Merge;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.noetzold.model.MediaModel;
import tech.noetzold.service.CatalogService;
import tech.noetzold.service.MediaService;

@ApplicationScoped
public class MediaConsumer {

    @Inject
    MediaService mediaService;

    @Inject
    CatalogService catalogService;

    private static final Logger logger = LoggerFactory.getLogger(MediaConsumer.class);

    @Incoming("medias")
    @Merge
    @Blocking
    public MediaModel process(MediaModel incomingMediaModel) {
        catalogService.sendMedia(incomingMediaModel);
        mediaService.saveMediaModel(incomingMediaModel);
        logger.info("Create Media " + incomingMediaModel.getMediaId() + ".");

        return incomingMediaModel;
    }
}
