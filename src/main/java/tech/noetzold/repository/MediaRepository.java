package tech.noetzold.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.noetzold.model.MediaModel;

@ApplicationScoped
public class MediaRepository implements PanacheRepository<MediaModel> {
}
