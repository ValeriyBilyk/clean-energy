package com.example.advokat.cleanenergy.rest.services;

import com.example.advokat.cleanenergy.entities.CurrentAsset;
import com.example.advokat.cleanenergy.entities.User;
import com.example.advokat.cleanenergy.entities.cost.Cost;
import com.example.advokat.cleanenergy.rest.requests.AuthRequest;
import com.example.advokat.cleanenergy.rest.requests.DataRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MainService {

    @POST("user/api/login")
    Call<User> login(@Query("username") String username, @Query("password") String password);

    @GET("types/info/currentAsset")
    Call<CurrentAsset> getCurrentAsset();

    @POST("user/api/getAllExpenditures")
    Call<Cost> getAllCosts(@Body AuthRequest authRequest);

    @Headers({
            "Content-Type: application/json"
    })
    @POST("user/api/expenditure/constant")
    Call<Integer> sendDataConstant(@Body DataRequest dataRequest);

    @Headers({
            "Content-Type: application/json"
    })
    @POST("user/api/expenditure/notParmenent")
    Call<Integer> sendDataParmenent(@Body DataRequest dataRequest);
}
