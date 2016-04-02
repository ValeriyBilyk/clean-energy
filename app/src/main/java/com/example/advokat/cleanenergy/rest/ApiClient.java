package com.example.advokat.cleanenergy.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static String url = "http://10.0.3.2:8080";
    private static Retrofit retrofit;

    private static void init() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static Retrofit retrofit() {
        if (retrofit == null) {
            init();
        }
        return retrofit;
    }

    public static void invalidate() {
        retrofit = null;
    }

}
