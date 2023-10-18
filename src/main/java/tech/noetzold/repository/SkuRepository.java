package tech.noetzold.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.noetzold.model.SkuModel;

@ApplicationScoped
public class SkuRepository implements PanacheRepository<SkuModel> {
}
