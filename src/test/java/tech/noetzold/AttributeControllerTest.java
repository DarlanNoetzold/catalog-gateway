package tech.noetzold;


import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import tech.noetzold.controller.AttributeController;
import tech.noetzold.model.AttributeModel;
import tech.noetzold.model.SkuModel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(AttributeController.class)
public class AttributeControllerTest {

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
    public void testGetAttributeModelById() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:4000/api/catalog/v1/attribute/{id}", "d6e35213-7757-4eb4-9a5a-755e2cde9011")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testSaveAttributeModel() {
        AttributeModel attributeModel = new AttributeModel();
        attributeModel.setName("Test Attribute");
        attributeModel.setDescription("Test Description");
        attributeModel.setType("Test Type");
        attributeModel.setImageUrl("https://example.com/image.jpg");
        attributeModel.setHexCode("#FFFFFF");
        attributeModel.setInternalName("Test Internal Name");
        attributeModel.setPriority("1");
        SkuModel skuModel = new SkuModel();
        skuModel.setSkuId(UUID.fromString("d7b9f47d-9a1a-4a3e-b4d8-9e3271632f13"));
        attributeModel.setSku(skuModel);

        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(attributeModel)
                .when()
                .post("http://localhost:4000/api/catalog/v1/attribute")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(3)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testUpdateAttributeModel() {
        AttributeModel updatedAttributeModel = new AttributeModel();
        updatedAttributeModel.setName("Updated Attribute");
        updatedAttributeModel.setDescription("Updated Description");
        updatedAttributeModel.setType("Updated Type");
        updatedAttributeModel.setImageUrl("https://example.com/new-image.jpg");
        updatedAttributeModel.setHexCode("#000000");
        updatedAttributeModel.setInternalName("Updated Internal Name");
        updatedAttributeModel.setPriority("Low");
        SkuModel skuModel = new SkuModel();
        skuModel.setSkuId(UUID.fromString("d7b9f47d-9a1a-4a3e-b4d8-9e3271632f13"));
        updatedAttributeModel.setSku(skuModel);

        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(updatedAttributeModel)
                .when()
                .put("http://localhost:4000/api/catalog/v1/attribute/{id}", "d6e35213-7757-4eb4-9a5a-755e2cde9011")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(4)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testDeleteAttributeModel() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .delete("http://localhost:4000/api/catalog/v1/attribute/{id}", "e5d8f39c-70a5-4e1e-9aa2-56a7c9ae2c31")
                .then()
                .statusCode(200);
    }
}
