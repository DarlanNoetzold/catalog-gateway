package tech.noetzold.customer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.noetzold.service.SkuService;

@ApplicationScoped
public class SkuConsumer {

    @Inject
    SkuService skuService;

    private static final Logger logger = LoggerFactory.getLogger(SkuConsumer.class);
}
