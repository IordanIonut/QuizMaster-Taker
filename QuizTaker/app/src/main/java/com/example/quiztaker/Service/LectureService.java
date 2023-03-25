package com.example.quiztaker.Service;

import com.example.quiztaker.Model.Lectures;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface LectureService {
    @POST("lectures/add")
    Call<Lectures> createLactures(@Body Lectures user);
    @GET("lectures/last")
    Call<Lectures> lastLecturesInset();
    @GET("lectures/course_id")
    Call<List<Lectures>> selectLecutreByCourseId(@Query("course_id") Long course_id);
    @GET("lectures/lecture_id")
    Call<Lectures> selectLectureByLectureId(@Query("lecture_id") Long lecture_id);
    @PUT("lectures/update")
    Call<Lectures> updateLectures(@Body Lectures lectures);
}
