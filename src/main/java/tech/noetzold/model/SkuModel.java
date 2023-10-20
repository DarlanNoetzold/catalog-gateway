package tech.noetzold.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.wildfly.common.annotation.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SkuModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID skuId;

    private String displayName;

    private String partnerId;

    private Long sellerId;

    private String ean;

    private BigDecimal price;

    private BigDecimal salePrice;

    private Long stockLevel;

    private Boolean enabled;

    private Dimension packageDimensionModel;

    private Dimension contentDimensionModel;

    private List<AttributeModel> attributes;

    @OneToMany(mappedBy = "sku")
    @Fetch(FetchMode.JOIN)
    private List<MediaModel> medias;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductModel product;

}
