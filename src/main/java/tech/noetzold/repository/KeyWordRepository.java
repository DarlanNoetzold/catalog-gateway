package tech.noetzold.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.noetzold.model.KeyWordModel;

@ApplicationScoped
public class KeyWordRepository implements PanacheRepository<KeyWordModel> {
}
