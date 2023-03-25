package com.example.quiztaker.Service;

import com.example.quiztaker.Model.UserLectures;
import com.example.quiztaker.Model.UserQuizzers;
import retrofit2.Call;
import retrofit2.http.*;

public interface UserQuizzerService {
    @POST("usersQuizzers/add")
    Call<UserQuizzers> createUserQuizzers (@Body UserQuizzers userQuizzers);
    @GET("usersQuizzers/user_id/quizzer_id")
    Call<UserQuizzers> selectUserQuizzesByUserIdAndQuizzerId(@Query("user_id") Long user_id, @Query("quizzer_id") Long quizzer_id);
    @DELETE("usersQuizzers/delete/{id}")
    Call<Void> deleteUserQuzzers(@Path("id") long id);


}
