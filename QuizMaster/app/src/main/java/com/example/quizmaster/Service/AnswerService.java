package com.example.quizmaster.Service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface AnswerService {
    @GET("answers/count/quizzer_id")
    Call<List<Object[]>> countAnswersByQuestionIdAndAnswer(@Query("quizzer_id") Long quizzer_id);
    @GET("answers/count/course_id")
    Call<List<Object[]>> countLenghtQuizzer(@Query("course_id") Long course_id);
}
