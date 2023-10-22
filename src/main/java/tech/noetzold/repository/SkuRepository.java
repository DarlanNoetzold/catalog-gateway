package tech.noetzold.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.noetzold.model.ProductModel;
import tech.noetzold.model.SkuModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class SkuRepository implements PanacheRepository<SkuModel> {

    public Optional<SkuModel> findByIdOptional(UUID id) {
        return find("skuId", id).firstResultOptional();
    }

    public void deleteById(UUID id) {
        delete("skuId", id);
    }

    public List<SkuModel> findByProductId(ProductModel productModel) {
        return list("product", productModel);
    }
}
