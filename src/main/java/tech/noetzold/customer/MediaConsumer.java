package tech.noetzold.customer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.noetzold.service.MediaService;

@ApplicationScoped
public class MediaConsumer {

    @Inject
    MediaService mediaService;

    private static final Logger logger = LoggerFactory.getLogger(MediaConsumer.class);
}
