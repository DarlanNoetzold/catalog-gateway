package tech.noetzold.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.noetzold.model.AttributeModel;
import tech.noetzold.model.CategoryModel;
import tech.noetzold.model.KeyWordModel;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<CategoryModel>{

    public Optional<CategoryModel> findByIdOptional(UUID id) {
        return find("categoryId", id).firstResultOptional();
    }

    public void deleteById(UUID id) {
        delete("categoryId", id);
    }
}
