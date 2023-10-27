package tech.noetzold;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import tech.noetzold.controller.MediaController;
import tech.noetzold.model.MediaModel;
import tech.noetzold.model.SkuModel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(MediaController.class)
public class MediaControllerTest {
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
    public void testGetMediaModelById() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:4000/api/catalog/v1/media/paymentId/{id}", "6bafc0a1-7b98-4e3c-90ac-d786cb17d5f8")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testSaveMediaModel() {
        MediaModel mediaModel = new MediaModel();
        mediaModel.setThumbnailImageURL("Thumbnail URL");
        mediaModel.setSmallImageUrl("Small Image URL");
        mediaModel.setMediumImageUrl("Medium Image URL");
        mediaModel.setLargeImageUrl("Large Image URL");
        mediaModel.setZoomImageUrl("Zoom Image URL");
        SkuModel skuModel = new SkuModel();
        skuModel.setSkuId(UUID.fromString("d7b9f47d-9a1a-4a3e-b4d8-9e3271632f13"));
        mediaModel.setSku(skuModel);

        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(mediaModel)
                .when()
                .post("http://localhost:4000/api/catalog/v1/media")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(3)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testUpdateMediaModel() {
        MediaModel updatedMediaModel = new MediaModel();
        updatedMediaModel.setThumbnailImageURL("Updated Thumbnail URL");
        updatedMediaModel.setSmallImageUrl("Updated Small Image URL");
        updatedMediaModel.setMediumImageUrl("Updated Medium Image URL");
        updatedMediaModel.setLargeImageUrl("Updated Large Image URL");
        updatedMediaModel.setZoomImageUrl("Updated Zoom Image URL");
        SkuModel skuModel = new SkuModel();
        skuModel.setSkuId(UUID.fromString("d7b9f47d-9a1a-4a3e-b4d8-9e3271632f13"));
        updatedMediaModel.setSku(skuModel);

        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(updatedMediaModel)
                .when()
                .put("http://localhost:4000/api/catalog/v1/media/{id}", "6bafc0a1-7b98-4e3c-90ac-d786cb17d5f8")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(4)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testDeleteMediaModel() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .delete("http://localhost:4000/api/catalog/v1/media/{id}", "26e89b76-183c-4a4b-9ac9-1dc9f3c0f5e7")
                .then()
                .statusCode(200);
    }
}
