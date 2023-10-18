package tech.noetzold.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.noetzold.model.MediaModel;
import tech.noetzold.model.ProductModel;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProductMedia implements PanacheRepository<ProductModel> {

    public Optional<ProductModel> findByIdOptional(UUID id) {
        return find("productId", id).firstResultOptional();
    }

    public void deleteById(UUID id) {
        delete("productId", id);
    }
}
