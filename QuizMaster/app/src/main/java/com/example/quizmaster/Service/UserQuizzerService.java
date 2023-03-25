package com.example.quizmaster.Service;

import com.example.quizmaster.Model.UserQuizzers;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface UserQuizzerService {
    @GET("usersQuizzers/course_id")
    Call<List<UserQuizzers>> selectUserQuizzesFromCourseId(@Query("course_id") Long course_id);
}
