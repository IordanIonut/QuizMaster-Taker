package com.example.quiztaker.Service;

import com.example.quiztaker.Model.Courses;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface CourseService {
    @POST("courses/add")
    Call<Courses> createCourse(@Body Courses user);
    @GET("courses/last")
    Call<Courses> lastCourseInset();
    @GET("courses/name/description")
    Call<List<Courses>> selectByNameAndDescription(@Query("description") String description, @Query("name") String name, @Query("user_id") Long user_id);
    @GET("courses/last/6/user_id")
    Call<List<Courses>> selectLast6CoursesByUserId(@Query("user_id") Long user_id);
    @GET("courses/all/user_id")
    Call<List<Courses>> selectAllCoursesByUserId(@Query("user_id") Long user_id);
    @GET("courses/course_id")
    Call<Courses> selectCourseByCourseId(@Query("course_id") Long course_id);
    @GET("courses/all/desc")
    Call<List<Courses>> selecltAllCoursesDesc();
    @GET("courses/complete/user_id/name")
    Call<List<Courses>> selectCursusesComplete(@Query("user_id") Long user_id, @Query("name") String name);
    @GET("courses/not/complete/user_id/name")
    Call<List<Courses>> selectCoursesNotCompletedName(@Query("user_id") Long user_id, @Query("name") String name);
    @GET("courses/started/user_id")
    Call<List<Courses>> selectCourseStarted(@Query("user_id") Long user_id);
    @GET("courses/not/abonament/user_id/name")
    Call<List<Courses>> selectCoursesNotAbonament(@Query("user_id") Long user_id, @Query("name") String name);
}
