package tech.noetzold.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class AuthResponse {
    private String access_token;

    public String getAccessToken() {
        return access_token;
    }
}
