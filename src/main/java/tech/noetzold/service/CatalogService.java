package tech.noetzold.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import tech.noetzold.client.ApiServiceClient;
import tech.noetzold.client.CatalogClient;
import tech.noetzold.model.*;

@ApplicationScoped
public class CatalogService {

    @Inject
    ApiService apiService;

    @Inject
    @RestClient
    CatalogClient catalogClient;

    public void sendSku(SkuModel skuModel){
        catalogClient.sendSku(apiService.loginAndGetToken(), skuModel);
    }

    public void sendProduct(ProductModel productModel){
        catalogClient.sendProduct(apiService.loginAndGetToken(), productModel);
    }

    public void sendMedia(MediaModel mediaModel){
        catalogClient.sendMedia(apiService.loginAndGetToken(), mediaModel);
    }

    public void sendKeyWord(KeyWordModel keyWordModel){
        catalogClient.sendKeyWord(apiService.loginAndGetToken(), keyWordModel);
    }

    public void sendCategory(CategoryModel categoryModel){
        catalogClient.sendCategory(apiService.loginAndGetToken(), categoryModel);
    }

    public void sendAttribute(AttributeModel attributeModel){
        catalogClient.sendAttribute(apiService.loginAndGetToken(), attributeModel);
    }

}
