package com.example.quiztaker.Model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserQuizzers {
    @SerializedName("user_quizze_id")
    private Long user_quizze_id;
    @SerializedName("user_id")
    private Users user_id;
    @SerializedName("quizzer_id")
    private Quizzers quizzer_id;
    @SerializedName("score")
    private int score;

    public UserQuizzers(Users user_id, Quizzers quizzer_id, int score) {
        this.user_id = user_id;
        this.quizzer_id = quizzer_id;
        this.score = score;
    }
}
