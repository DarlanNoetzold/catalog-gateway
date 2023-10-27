package tech.noetzold;


import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import tech.noetzold.controller.CategoryController;
import tech.noetzold.model.CategoryModel;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(CategoryController.class)
public class CategoryControllerTest {

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
    public void testGetCategoryModelById() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:4000/api/catalog/v1/category/{id}", "f05baf54-ae7b-4922-9a49-2c0a5252b77c")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testSaveCategoryModel() {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName("Test Category");

        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(categoryModel)
                .when()
                .post("http://localhost:4000/api/catalog/v1/category")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(3)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testUpdateCategoryModel() {
        CategoryModel updatedCategoryModel = new CategoryModel();
        updatedCategoryModel.setName("Updated Category");

        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(updatedCategoryModel)
                .when()
                .put("http://localhost:4000/api/catalog/v1/category/{id}", "f05baf54-ae7b-4922-9a49-2c0a5252b77c")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(4)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testDeleteCategoryModel() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .delete("http://localhost:4000/api/catalog/v1/category/{id}", "f9cebd5b-45f3-43c6-8e4a-72d3b9ea3c7c")
                .then()
                .statusCode(200);
    }
}
