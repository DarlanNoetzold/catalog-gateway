package tech.noetzold.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.noetzold.model.KeyWordModel;
import tech.noetzold.model.MediaModel;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class MediaRepository implements PanacheRepository<MediaModel> {

    public Optional<MediaModel> findByIdOptional(UUID id) {
        return find("mediaId", id).firstResultOptional();
    }

    public void deleteById(UUID id) {
        delete("mediaId", id);
    }
}
