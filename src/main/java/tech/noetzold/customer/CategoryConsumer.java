package tech.noetzold.customer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.noetzold.service.CategoryService;

@ApplicationScoped
public class CategoryConsumer {

    @Inject
    CategoryService categoryService;

    private static final Logger logger = LoggerFactory.getLogger(CategoryConsumer.class);
}