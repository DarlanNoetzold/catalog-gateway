package tech.noetzold.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.noetzold.model.ProductModel;

@ApplicationScoped
public class ProductMedia implements PanacheRepository<ProductModel> {
}
