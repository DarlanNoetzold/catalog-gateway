package tech.noetzold;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import tech.noetzold.controller.ProductController;
import tech.noetzold.model.CategoryModel;
import tech.noetzold.model.ProductModel;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(ProductController.class)
public class ProductControllerTest {

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
    public void testGetProductModelById() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:4000/api/catalog/v1/product/{id}", "8a9e9d58-4613-43e4-a090-70f98b157c86")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testSaveProductModel() {
        ProductModel productModel = new ProductModel();
        productModel.setTitle("Product Title");
        productModel.setDescription("Product Description");
        productModel.setBrand("Product Brand");
        productModel.setModel("Product Model");
        productModel.setVideoUrl("Product Video URL");
        productModel.setGender("Product Gender");
        productModel.setWarrantyTime(12);
        productModel.setWarrantyText("Product Warranty Text");
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCategoryId(UUID.fromString("f05baf54-ae7b-4922-9a49-2c0a5252b77c"));
        productModel.setCategory(categoryModel);

        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(productModel)
                .when()
                .post("http://localhost:4000/api/catalog/v1/product")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(3)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testUpdateProductModel() {
        ProductModel updatedProductModel = new ProductModel();
        updatedProductModel.setTitle("Updated Product Title");
        updatedProductModel.setDescription("Updated Product Description");
        updatedProductModel.setBrand("Updated Product Brand");
        updatedProductModel.setModel("Updated Product Model");
        updatedProductModel.setVideoUrl("Updated Product Video URL");
        updatedProductModel.setGender("Updated Product Gender");
        updatedProductModel.setWarrantyTime(24);
        updatedProductModel.setWarrantyText("Updated Product Warranty Text");
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCategoryId(UUID.fromString("f05baf54-ae7b-4922-9a49-2c0a5252b77c"));
        updatedProductModel.setCategory(categoryModel);

        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(updatedProductModel)
                .when()
                .put("http://localhost:4000/api/catalog/v1/product/{id}", "8a9e9d58-4613-43e4-a090-70f98b157c86")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(4)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testDeleteProductModel() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .delete("http://localhost:4000/api/catalog/v1/product/{id}", "c5bdaa1d-88c7-47a5-9a63-d8c4e8ad17d8")
                .then()
                .statusCode(200);
    }
}
