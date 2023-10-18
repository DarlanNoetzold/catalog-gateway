package tech.noetzold.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.noetzold.model.AttributeModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AttributeRepository implements PanacheRepository<AttributeModel> {

    public Optional<AttributeModel> findByIdOptional(UUID id) {
        return find("attributeId", id).firstResultOptional();
    }

    public void deleteById(UUID id) {
        delete("attributeId", id);
    }
}
