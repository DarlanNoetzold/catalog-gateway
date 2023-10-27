package tech.noetzold;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import tech.noetzold.controller.SkuController;
import tech.noetzold.model.ProductModel;
import tech.noetzold.model.SkuModel;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(SkuController.class)
public class SkuControllerTest {

    private String accessToken;

    @BeforeEach
    public void obtainAccessToken() {
        final String username = "admin";
        final String password = "admin";

        final String tokenEndpoint = "http://localhost:8180/realms/quarkus1/protocol/openid-connect/token";

        final Map<String, String> requestData = new HashMap<>();
        requestData.put("username", username);
        requestData.put("password", password);
        requestData.put("grant_type", "password");

        final Response response = given()
                .auth().preemptive().basic("backend-service", "secret")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParams(requestData)
                .when()
                .post(tokenEndpoint);

        response.then().statusCode(200);

        this.accessToken = response.jsonPath().getString("access_token");
    }

    @Test
    @Order(1)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testGetSkuModelById() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:4000/api/catalog/v1/sku/{id}", "d7b9f47d-9a1a-4a3e-b4d8-9e3271632f13")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testGetAllSkuByProductId() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:4000/api/catalog/v1/sku/getSkus/{id}", "8a9e9d58-4613-43e4-a090-70f98b157c86")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(3)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testSaveSkuModel() {
        SkuModel skuModel = new SkuModel();
        skuModel.setDisplayName("Sku Display Name");
        skuModel.setPartnerId("Partner ID");
        skuModel.setSellerId(123L);
        skuModel.setEan("EAN Code");
        skuModel.setPrice(new BigDecimal("100.00"));
        skuModel.setSalePrice(new BigDecimal("90.00"));
        skuModel.setStockLevel(50L);
        skuModel.setEnabled(true);
        ProductModel productModel = new ProductModel();
        productModel.setProductId(UUID.fromString("8a9e9d58-4613-43e4-a090-70f98b157c86"));
        skuModel.setProduct(productModel);

        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(skuModel)
                .when()
                .post("http://localhost:4000/api/catalog/v1/sku")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(4)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testUpdateSkuModel() {
        SkuModel updatedSkuModel = new SkuModel();
        updatedSkuModel.setDisplayName("Updated Sku Display Name");
        updatedSkuModel.setPartnerId("Updated Partner ID");
        updatedSkuModel.setSellerId(456L);
        updatedSkuModel.setEan("Updated EAN Code");
        updatedSkuModel.setPrice(new BigDecimal("200.00"));
        updatedSkuModel.setSalePrice(new BigDecimal("180.00"));
        updatedSkuModel.setStockLevel(100L);
        updatedSkuModel.setEnabled(false);
        ProductModel productModel = new ProductModel();
        productModel.setProductId(UUID.fromString("8a9e9d58-4613-43e4-a090-70f98b157c86"));
        updatedSkuModel.setProduct(productModel);

        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(updatedSkuModel)
                .when()
                .put("http://localhost:4000/api/catalog/v1/sku/{id}", "d7b9f47d-9a1a-4a3e-b4d8-9e3271632f13")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(5)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testDeleteSkuModel() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .delete("http://localhost:4000/api/catalog/v1/sku/{id}", "f9cebd5b-45f3-43c6-8e4a-72d3b9ea3c7c")
                .then()
                .statusCode(200);
    }
}
