package com.example.quizmaster.Service;

import com.example.quizmaster.Model.Lectures;
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
    @DELETE("lectures/delete/lecture_id")
    Call<Void> deleteLecturesByLectureId(@Query("lecture_id") Long lecture_id);
}
