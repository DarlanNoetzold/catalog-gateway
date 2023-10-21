package tech.noetzold.customer;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.annotations.Merge;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.noetzold.model.CategoryModel;
import tech.noetzold.service.CategoryService;

@ApplicationScoped
public class CategoryConsumer {

    @Inject
    CategoryService categoryService;

    private static final Logger logger = LoggerFactory.getLogger(CategoryConsumer.class);

    @Incoming("categories")
    @Merge
    @Blocking
    public CategoryModel process(JsonObject incomingCategoryModelInJson) {

        CategoryModel incomingCategoryModel = incomingCategoryModelInJson.mapTo(CategoryModel.class);

        categoryService.saveCategoryModel(incomingCategoryModel);
        logger.info("Create Category " + incomingCategoryModel.getCategoryId() + ".");

        return incomingCategoryModel;
    }
}
