package tech.noetzold.customer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.noetzold.service.KeyWordService;

@ApplicationScoped
public class KeyWordConsumer {

    @Inject
    KeyWordService keyWordService;

    private static final Logger logger = LoggerFactory.getLogger(KeyWordConsumer.class);
}