package com.example.quizmaster.Service;

import com.example.quizmaster.Model.UserCourses;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface UserCourseService {
    @GET("usersCourses/all/abonamentioned/user_id")
    Call<List<UserCourses>> selecltAllUsersCoursesAbonmentioned(@Query("user_id") Long user_id);
}
