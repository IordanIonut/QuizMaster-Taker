package com.example.quiztaker.Service;

import com.example.quiztaker.Model.Lectures;
import com.example.quiztaker.Model.UserLectures;
import com.example.quiztaker.Model.Users;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface UserLectureService {
    @POST("userLectures/add")
    Call<UserLectures> createUserLectures (@Body UserLectures userLectures);
    @GET("userLectures/user_id/lecture_id")
    Call<UserLectures> selectUserLecturesByUserIdAndLectureId(@Query("user_id") Long user_id, @Query("lecture_id") Long lecuture_id);
    @GET("userLectures/user_id")
    Call<List<UserLectures>> selectUserLecturesByUserId(@Query("user_id") Long user_id);
}
