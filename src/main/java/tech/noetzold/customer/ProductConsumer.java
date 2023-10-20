package tech.noetzold.customer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.noetzold.service.ProductService;

@ApplicationScoped
public class ProductConsumer {

    @Inject
    ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductConsumer.class);
}
