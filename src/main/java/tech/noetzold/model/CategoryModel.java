package tech.noetzold.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
import java.util.UUID;

public class CategoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID categoryId;

    private String name;

    @OneToMany(mappedBy = "category")
    @Fetch(FetchMode.JOIN)
    private List<ProductModel> products;
}
