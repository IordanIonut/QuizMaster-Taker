package com.example.quizmaster.Service;

import com.example.quizmaster.Model.Courses;
import retrofit2.Call;
import retrofit2.http.*;

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
    @DELETE("courses/delete/course_id")
    Call<Void> deleteCoursesByCourseId(@Query("course_id") Long course_id);
    @GET("courses/count/course_id")
    Call<Object[]> selectLecturesCounter(@Query("course_id") Long course_id);
    @GET("courses/name")
    Call<List<Courses>> selectCoursesByName(@Query("name") String name, @Query("user_id") Long user_id);
}
