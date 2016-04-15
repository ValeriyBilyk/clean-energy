package com.example.advokat.cleanenergy.rest.services;

import com.example.advokat.cleanenergy.entities.CurrentAsset;
import com.example.advokat.cleanenergy.entities.User;
import com.example.advokat.cleanenergy.entities.cost.Cost;
import com.example.advokat.cleanenergy.entities.income.IncomeCategory;
import com.example.advokat.cleanenergy.rest.requests.AuthRequest;
import com.example.advokat.cleanenergy.rest.requests.DataRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MainService {

    @POST("user/api/login")
    Call<User> login(@Query("username") String username, @Query("password") String password);

    @GET("user/api/currentAsset")
    Call<CurrentAsset> getCurrentAsset(@Query("accessKey") String accessKey);

    @POST("user/api/getAllExpenditures")
    Call<Cost> getAllCosts(@Body AuthRequest authRequest);

    @Headers({
            "Content-Type: application/json"
    })
    @POST("user/api/expenditure/add/constant")
    Call<Integer> sendDataConstant(@Body DataRequest dataRequest);

    @Headers({
            "Content-Type: application/json"
    })
    @POST("user/api/expenditure/add/notParmenent")
    Call<Integer> sendDataParmenent(@Body DataRequest dataRequest);

    @POST("user/api/expenditure/edit/constant")
    Call<Integer> editConstantData(@Body DataRequest dataRequest);

    @POST("user/api/expenditure/edit/notParmenent")
    Call<Integer> editNotParmenent(@Body DataRequest dataRequest);

    @GET("user/api/income/types")
    Call<IncomeCategory> getAllIncome(@Query("accessKey") String accessKey);


    Call<ResponseBody> deleteIncome(long id);
}
