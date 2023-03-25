package com.example.quiztaker.Service;

import com.example.quiztaker.Model.UserCourses;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface UserCourseService {
    @POST("usersCourses/add")
    Call<UserCourses> addUserCourse(@Body UserCourses userCourses);
    @GET("usersCourses/user_id/course_id")
    Call<UserCourses> selectUserCourseByUserIdAndCourseId(@Query("user_id") Long user_id, @Query("course_id") Long course_id);
    @GET("usersCourses/all/user_id")
    Call<List<UserCourses>> selectAllUserCoursesByUserId(@Query("user_id") Long user_id);
}
