package tech.noetzold.service;

import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import tech.noetzold.model.ProductModel;
import tech.noetzold.model.ProductModel;
import tech.noetzold.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    @Transactional
    public List<ProductModel> findAll(int page, int size, String sortBy) {
        Sort sort = Sort.ascending(sortBy);
        PanacheQuery<ProductModel> query = productRepository.findAll(sort);

        int offset = (page - 1) * size;
        return query.range(offset, (size-1)*page).list();
    }

    @Transactional
    public List<ProductModel> findAllProductModel(){
        PanacheQuery<ProductModel> allProductModel = productRepository.findAll();
        return allProductModel.list();
    }

    @Transactional
    @CacheResult(cacheName = "product")
    public ProductModel findProductModelById(UUID id){
        Optional<ProductModel> optionalProductModel = productRepository.findByIdOptional(id);
        return optionalProductModel.orElse(new ProductModel());
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "product")
    public ProductModel saveProductModel(ProductModel productModel){
        productRepository.persist(productModel);
        productRepository.flush();
        return productModel;
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "product")
    public void updateProductModel(ProductModel productModel){
        if (productModel == null || productModel.getProductId() == null) {
            throw new WebApplicationException("Invalid data for productModel update", Response.Status.BAD_REQUEST);
        }

        ProductModel existingProductModel = findProductModelById(productModel.getProductId());
        if (existingProductModel == null) {
            throw new WebApplicationException("productModel not found", Response.Status.NOT_FOUND);
        }

        existingProductModel.setModel(productModel.getModel());
        existingProductModel.setCode(productModel.getCode());
        existingProductModel.setBrand(productModel.getBrand());
        existingProductModel.setCategory(productModel.getCategory());
        existingProductModel.setGender(productModel.getGender());
        existingProductModel.setAllowAutomaticSkuMarketplaceCreation(productModel.getAllowAutomaticSkuMarketplaceCreation());
        existingProductModel.setCalculatedPrice(productModel.getCalculatedPrice());
        existingProductModel.setDefinitionPriceScope(productModel.getDefinitionPriceScope());
        existingProductModel.setDescription(productModel.getDescription());
        existingProductModel.setHasVariations(productModel.getHasVariations());
        existingProductModel.setHeight(productModel.getHeight());
        existingProductModel.setLength(productModel.getLength());
        existingProductModel.setMessage(productModel.getMessage());
        existingProductModel.setPriceFactor(productModel.getPriceFactor());
        existingProductModel.setTitle(productModel.getTitle());
        existingProductModel.setWarrantyText(productModel.getWarrantyText());
        existingProductModel.setVideoUrl(productModel.getVideoUrl());
        existingProductModel.setWarrantyTime(productModel.getWarrantyTime());
        existingProductModel.setWeight(productModel.getWeight());
        existingProductModel.setWidth(productModel.getWidth());
        existingProductModel.setIntegratedDate(productModel.getIntegratedDate());
        existingProductModel.setIntegrated(productModel.getIntegrated());

        productRepository.persist(existingProductModel);
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "product")
    public void deleteProductModelById(UUID id){
        productRepository.deleteById(id);
    }
}
