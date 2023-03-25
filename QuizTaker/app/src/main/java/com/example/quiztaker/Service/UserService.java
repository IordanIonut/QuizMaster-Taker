package com.example.quiztaker.Service;

import com.example.quiztaker.Model.LoginRequest;
import com.example.quiztaker.Model.LoginResponse;
import com.example.quiztaker.Model.Users;
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
    @GET("users/user_id")
    Call<Users> selectUserByUserId(@Query("user_id") Long user_id);
    @GET("users/token")
    Call<String> userToken(@Query("token") String token);
}
