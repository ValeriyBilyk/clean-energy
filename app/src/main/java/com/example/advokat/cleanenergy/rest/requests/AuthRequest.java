package com.example.advokat.cleanenergy.rest.requests;

public class AuthRequest {

    private String accessKey;

    public AuthRequest(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
}
