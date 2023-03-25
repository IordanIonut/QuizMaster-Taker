package com.example.quizmaster.Service;

import com.example.quizmaster.Model.LoginRequest;
import com.example.quizmaster.Model.LoginResponse;
import com.example.quizmaster.Model.Users;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {
    @POST("users/add")
    Call<Users> createUser(@Body Users user);
    @GET("users/email")
    Call<Users> findByEmail(@Query("email") String email);
    @GET("users/phone_number")
    Call<Users> findByPhone_number(@Query("phoneNumber") String phoneNumber);
    @POST("users/login")
    Call<LoginResponse> authenticateUser(@Body LoginRequest loginRequest);
    @GET("users/token")
    Call<String> userToken(@Query("token") String token);
}
