package com.example.advokat.cleanenergy.rest;

import android.content.Context;
import android.widget.Toast;

import com.example.advokat.cleanenergy.rest.services.MainService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static String url = "http://10.0.3.2:8080";
    private static ApiClient instance;
    private MainService mainService;

    public ApiClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mainService = retrofit.create(MainService.class);
    }

    public static ApiClient retrofit() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public MainService getMainService() {
        return mainService;
    }

    public static void onError(String message, Context context) {
        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
