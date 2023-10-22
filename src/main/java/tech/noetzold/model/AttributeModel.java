package tech.noetzold.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AttributeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID attributeId;

    private String name;

    private String description;

    private String type;

    private String imageUrl;

    private String hexCode;

    private String internalName;

    private String priority;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "skuId")
    private SkuModel sku;

}
