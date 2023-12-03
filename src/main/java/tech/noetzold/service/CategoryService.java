package tech.noetzold.service;

import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import tech.noetzold.model.AttributeModel;
import tech.noetzold.model.CategoryModel;
import tech.noetzold.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CategoryService {
    
    @Inject
    CategoryRepository categoryRepository;

    @Transactional
    @CacheResult(cacheName = "category")
    public CategoryModel findCategoryModelById(UUID id){
        Optional<CategoryModel> optionalCategoryModel = categoryRepository.findByIdOptional(id);
        return optionalCategoryModel.orElse(new CategoryModel());
    }

    @Transactional
    public List<CategoryModel> findAllCategoryModel(){
        PanacheQuery<CategoryModel> allCategoryModel = categoryRepository.findAll();
        return allCategoryModel.list();
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "category")
    public CategoryModel saveCategoryModel(CategoryModel categoryModel){
        categoryRepository.persist(categoryModel);
        categoryRepository.flush();
        return categoryModel;
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "category")
    public void updateCategoryModel(CategoryModel categoryModel){
        if (categoryModel == null || categoryModel.getCategoryId() == null) {
            throw new WebApplicationException("Invalid data for categoryModel update", Response.Status.BAD_REQUEST);
        }

        CategoryModel existingCategoryModel = findCategoryModelById(categoryModel.getCategoryId());
        if (existingCategoryModel == null) {
            throw new WebApplicationException("categoryModel not found", Response.Status.NOT_FOUND);
        }

        existingCategoryModel.setName(categoryModel.getName());
        existingCategoryModel.setIntegrated(categoryModel.getIntegrated());
        existingCategoryModel.setIntegratedDate(categoryModel.getIntegratedDate());

        categoryRepository.persist(existingCategoryModel);
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "category")
    public void deleteCategoryModelById(UUID id){
        categoryRepository.deleteById(id);
    }
}
