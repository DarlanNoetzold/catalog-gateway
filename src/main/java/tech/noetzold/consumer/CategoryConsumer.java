package tech.noetzold.consumer;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.annotations.Merge;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.noetzold.model.CategoryModel;
import tech.noetzold.service.CatalogService;
import tech.noetzold.service.CategoryService;

@ApplicationScoped
public class CategoryConsumer {

    @Inject
    CategoryService categoryService;

    @Inject
    CatalogService catalogService;

    private static final Logger logger = LoggerFactory.getLogger(CategoryConsumer.class);

    @Incoming("categories")
    @Merge
    @Blocking
    public CategoryModel process(CategoryModel incomingCategoryModel) {
        catalogService.sendCategory(incomingCategoryModel);
        categoryService.saveCategoryModel(incomingCategoryModel);
        logger.info("Create Category " + incomingCategoryModel.getCategoryId() + ".");

        return incomingCategoryModel;
    }
}
