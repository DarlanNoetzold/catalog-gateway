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
import tech.noetzold.repository.AttributeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AttributeService {

    @Inject
    AttributeRepository attributeRepository;

    @Transactional
    @CacheResult(cacheName = "attribute")
    public AttributeModel findAttributeModelById(UUID id){
        Optional<AttributeModel> optionalAttributeModel = attributeRepository.findByIdOptional(id);
        return optionalAttributeModel.orElse(new AttributeModel());
    }
    @Transactional
    public List<AttributeModel> findAllAttributeModel(){
        PanacheQuery<AttributeModel> allAttributeModel = attributeRepository.findAll();
        return allAttributeModel.list();
    }


    @Transactional
    @CacheInvalidateAll(cacheName = "attribute")
    public AttributeModel saveAttributeModel(AttributeModel attributeModel){
        attributeRepository.persist(attributeModel);
        attributeRepository.flush();
        return attributeModel;
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "attribute")
    public void updateAttributeModel(AttributeModel attributeModel){
        if (attributeModel == null || attributeModel.getAttributeId() == null) {
            throw new WebApplicationException("Invalid data for attributeModel update", Response.Status.BAD_REQUEST);
        }

        AttributeModel existingAttributeModel = findAttributeModelById(attributeModel.getAttributeId());
        if (existingAttributeModel == null) {
            throw new WebApplicationException("attributeModel not found", Response.Status.NOT_FOUND);
        }

        existingAttributeModel.setName(attributeModel.getName());
        existingAttributeModel.setDescription(attributeModel.getDescription());
        existingAttributeModel.setType(attributeModel.getType());
        existingAttributeModel.setPriority(attributeModel.getPriority());
        existingAttributeModel.setHexCode(attributeModel.getHexCode());
        existingAttributeModel.setImageUrl(attributeModel.getImageUrl());
        existingAttributeModel.setInternalName(attributeModel.getInternalName());
        existingAttributeModel.setIntegrated(attributeModel.getIntegrated());
        existingAttributeModel.setIntegratedDate(attributeModel.getIntegratedDate());

        attributeRepository.persist(existingAttributeModel);
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "attribute")
    public void deleteAttributeModelById(UUID id){
        attributeRepository.deleteById(id);
    }
}
