package tech.noetzold.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wildfly.common.annotation.NotNull;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MediaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID mediaId;

    private String thumbnailImageURL;

    private String smallImageUrl;

    private String mediumImageUrl;

    private String largeImageUrl;

    private String zoomImageUrl;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "skuId")
    private SkuModel sku;
}
