package tech.noetzold.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.Hibernate;
import tech.noetzold.model.ProductModel;
import tech.noetzold.model.SkuModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class SkuRepository implements PanacheRepository<SkuModel> {

    public Optional<SkuModel> findByIdOptional(UUID id) {
        Optional<SkuModel> skuModel = find("skuId", id).firstResultOptional();
        if(skuModel.isPresent()) {
            Hibernate.initialize(skuModel.get().getMedias());
            Hibernate.initialize(skuModel.get().getAttributes());
            Hibernate.initialize(skuModel.get().getKeywords());
        }
        return find("skuId", id).firstResultOptional();
    }

    public void deleteById(UUID id) {
        delete("skuId", id);
    }

    public List<SkuModel> findByProductId(ProductModel productModel) {
        List<SkuModel> listSkuModel = list("product", productModel);
        for (SkuModel skuModel: listSkuModel) {
            Hibernate.initialize(skuModel.getMedias());
            Hibernate.initialize(skuModel.getAttributes());
            Hibernate.initialize(skuModel.getKeywords());
        }
        return listSkuModel;
    }
}
