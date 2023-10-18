package tech.noetzold.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.noetzold.model.CategoryModel;
import tech.noetzold.model.KeyWordModel;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class KeyWordRepository implements PanacheRepository<KeyWordModel> {

    public Optional<KeyWordModel> findByIdOptional(UUID id) {
        return find("keyWordId", id).firstResultOptional();
    }

    public void deleteById(UUID id) {
        delete("keyWordId", id);
    }
}
