package tech.noetzold.service;

import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import tech.noetzold.model.MediaModel;
import tech.noetzold.repository.MediaRepository;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class MediaService {

    @Inject
    MediaRepository mediaRepository;

    @Transactional
    @CacheResult(cacheName = "media")
    public MediaModel findMediaModelById(UUID id){
        Optional<MediaModel> optionalMediaModel = mediaRepository.findByIdOptional(id);
        return optionalMediaModel.orElse(new MediaModel());
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "media")
    public MediaModel saveMediaModel(MediaModel mediaModel){
        mediaRepository.persist(mediaModel);
        mediaRepository.flush();
        return mediaModel;
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "media")
    public void updateMediaModel(MediaModel mediaModel){
        if (mediaModel == null || mediaModel.getMediaId() == null) {
            throw new WebApplicationException("Invalid data for mediaModel update", Response.Status.BAD_REQUEST);
        }

        MediaModel existingMediaModel = findMediaModelById(mediaModel.getMediaId());
        if (existingMediaModel == null) {
            throw new WebApplicationException("mediaModel not found", Response.Status.NOT_FOUND);
        }

        existingMediaModel.setSku(mediaModel.getSku());
        existingMediaModel.setLargeImageUrl(mediaModel.getLargeImageUrl());
        existingMediaModel.setSmallImageUrl(mediaModel.getSmallImageUrl());
        existingMediaModel.setMediumImageUrl(mediaModel.getMediumImageUrl());
        existingMediaModel.setZoomImageUrl(mediaModel.getZoomImageUrl());
        existingMediaModel.setThumbnailImageURL(mediaModel.getThumbnailImageURL());


        mediaRepository.persist(existingMediaModel);
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "media")
    public void deleteMediaModelById(UUID id){
        mediaRepository.deleteById(id);
    }
}
