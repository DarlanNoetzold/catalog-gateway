package tech.noetzold;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import tech.noetzold.controller.KeyWordController;
import tech.noetzold.model.KeyWordModel;
import tech.noetzold.model.SkuModel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(KeyWordController.class)
public class KeyWordControllerTest {

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
    public void testGetKeyWordModelById() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:4000/api/catalog/v1/keyword/{id}", "c5e80b92-8d5f-4cb5-9181-42d418eb5b6a")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testSaveKeyWordModel() {
        KeyWordModel keyWordModel = new KeyWordModel();
        keyWordModel.setKeyWord("Test Keyword");
        SkuModel skuModel = new SkuModel();
        skuModel.setSkuId(UUID.fromString("d7b9f47d-9a1a-4a3e-b4d8-9e3271632f13"));
        keyWordModel.setSku(skuModel);

        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(keyWordModel)
                .when()
                .post("http://localhost:4000/api/catalog/v1/keyword")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(3)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testUpdateKeyWordModel() {
        KeyWordModel updatedKeyWordModel = new KeyWordModel();
        updatedKeyWordModel.setKeyWord("Updated Keyword");
        SkuModel skuModel = new SkuModel();
        skuModel.setSkuId(UUID.fromString("d7b9f47d-9a1a-4a3e-b4d8-9e3271632f13"));
        updatedKeyWordModel.setSku(skuModel);

        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(updatedKeyWordModel)
                .when()
                .put("http://localhost:4000/api/catalog/v1/keyword/{id}", "c5e80b92-8d5f-4cb5-9181-42d418eb5b6a")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(4)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testDeleteKeyWordModel() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .delete("http://localhost:4000/api/catalog/v1/keyword/{id}", "f8d7a61e-cc6a-496d-8d8a-d7f86a1b4e3d")
                .then()
                .statusCode(200);
    }
}
