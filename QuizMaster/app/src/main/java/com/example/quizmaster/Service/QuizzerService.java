package com.example.quizmaster.Service;

import com.example.quizmaster.Model.Quizzers;
import retrofit2.Call;
import retrofit2.http.*;

public interface QuizzerService {
    @POST("quizzers/add")
    Call<Quizzers> createQuizzer(@Body Quizzers user);
    @GET("quizzers/last")
    Call<Quizzers> lastQuizzerInset();
    @GET("quizzers/lecture_id")
    Call<Quizzers> selectByLectureId(@Query("lecture_id") Long lectureId);
    @PUT("quizzers/update")
    Call<Quizzers> updateQuizzers(@Body Quizzers quizzers);
}
