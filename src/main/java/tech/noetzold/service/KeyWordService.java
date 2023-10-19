package tech.noetzold.service;

import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import tech.noetzold.model.KeyWordModel;
import tech.noetzold.repository.KeyWordRepository;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class KeyWordService {

    @Inject
    KeyWordRepository keyWordRepository;

    @Transactional
    @CacheResult(cacheName = "keyWord")
    public KeyWordModel findKeyWordModelById(UUID id){
        Optional<KeyWordModel> optionalKeyWordModel = keyWordRepository.findByIdOptional(id);
        return optionalKeyWordModel.orElse(new KeyWordModel());
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "keyWord")
    public KeyWordModel saveKeyWordModel(KeyWordModel keyWordModel){
        keyWordRepository.persist(keyWordModel);
        keyWordRepository.flush();
        return keyWordModel;
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "keyWord")
    public void updateKeyWordModel(KeyWordModel keyWordModel){
        if (keyWordModel == null || keyWordModel.getKeyWordId() == null) {
            throw new WebApplicationException("Invalid data for keyWordModel update", Response.Status.BAD_REQUEST);
        }

        KeyWordModel existingKeyWordModel = findKeyWordModelById(keyWordModel.getKeyWordId());
        if (existingKeyWordModel == null) {
            throw new WebApplicationException("keyWordModel not found", Response.Status.NOT_FOUND);
        }

        existingKeyWordModel.setKeyWord(keyWordModel.getKeyWord());

        keyWordRepository.persist(existingKeyWordModel);
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "keyWord")
    public void deleteKeyWordModelById(UUID id){
        keyWordRepository.deleteById(id);
    }
}
