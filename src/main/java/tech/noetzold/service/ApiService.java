package tech.noetzold.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import tech.noetzold.client.ApiServiceClient;
import tech.noetzold.model.AuthResponse;
import tech.noetzold.model.LoginData;

@ApplicationScoped
public class ApiService {

    @Inject
    @RestClient
    ApiServiceClient apiServiceClient;

    private String token;

    public String loginAndGetToken() {
        // Dados de login
        LoginData loginData = new LoginData();
        loginData.setEmail("admin@mail.com");
        loginData.setPassword("password");

        // Realiza o login e obtém o token
        AuthResponse response = apiServiceClient.authenticate(loginData);
        if (response != null) {
            token = response.getAccessToken();
        }
        return "Bearer " + token;
    }
}
