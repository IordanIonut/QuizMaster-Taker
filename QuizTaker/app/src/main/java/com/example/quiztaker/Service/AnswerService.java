package com.example.quiztaker.Service;

import com.example.quiztaker.Model.Answers;
import com.example.quiztaker.Model.Lectures;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AnswerService {
    @POST("answers/add")
    Call<Answers> createAnswer(@Body Answers answers);
}
