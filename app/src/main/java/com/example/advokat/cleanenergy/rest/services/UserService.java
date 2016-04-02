package com.example.advokat.cleanenergy.rest.services;

import com.example.advokat.cleanenergy.entities.User;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @POST("user/api/login")
    Call<User> login(@Query("username") String username, @Query("password") String password);

}
