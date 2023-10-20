package tech.noetzold.service;

import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import tech.noetzold.model.SkuModel;
import tech.noetzold.repository.SkuRepository;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class SkuService {

    @Inject
    SkuRepository skuRepository;

    @Transactional
    @CacheResult(cacheName = "sku")
    public SkuModel findSkuModelById(UUID id){
        Optional<SkuModel> optionalSkuModel = skuRepository.findByIdOptional(id);
        return optionalSkuModel.orElse(new SkuModel());
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "sku")
    public SkuModel saveSkuModel(SkuModel skuModel){
        skuRepository.persist(skuModel);
        skuRepository.flush();
        return skuModel;
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "sku")
    public void updateSkuModel(SkuModel skuModel){
        if (skuModel == null || skuModel.getSkuId() == null) {
            throw new WebApplicationException("Invalid data for skuModel update", Response.Status.BAD_REQUEST);
        }

        SkuModel existingSkuModel = findSkuModelById(skuModel.getSkuId());
        if (existingSkuModel == null) {
            throw new WebApplicationException("skuModel not found", Response.Status.NOT_FOUND);
        }

        existingSkuModel.setAttributes(skuModel.getAttributes());
        existingSkuModel.setEan(skuModel.getEan());
        existingSkuModel.setContentDimensionModel(skuModel.getContentDimensionModel());
        existingSkuModel.setEnabled(skuModel.getEnabled());
        existingSkuModel.setPackageDimensionModel(skuModel.getPackageDimensionModel());
        existingSkuModel.setDisplayName(skuModel.getDisplayName());
        existingSkuModel.setPartnerId(skuModel.getPartnerId());
        existingSkuModel.setProduct(skuModel.getProduct());
        existingSkuModel.setSalePrice(skuModel.getSalePrice());
        existingSkuModel.setStockLevel(skuModel.getStockLevel());
        existingSkuModel.setMedias(skuModel.getMedias());


        skuRepository.persist(existingSkuModel);
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "sku")
    public void deleteSkuModelById(UUID id){
        skuRepository.deleteById(id);
    }
}
