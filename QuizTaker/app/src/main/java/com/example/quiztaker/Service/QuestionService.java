package com.example.quiztaker.Service;

import com.example.quiztaker.Model.Questions;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface QuestionService {
    @POST("questions/add")
    Call<Questions> createQuestions(@Body Questions questions);
    @GET("questions/last")
    Call<Questions> lastQuestionInset();
    @GET("questions/quizzer_id")
    Call<List<Questions>> selectByQuizzerId(@Query("quizzer_id") Long quizzer_id);
    @PUT("questions/update")
    Call<Questions> updateQuestions (@Body Questions questions);
    @DELETE("questions/delete/{id}")
    Call<Void> deleteQuestions(@Path("id") Long id);
}
