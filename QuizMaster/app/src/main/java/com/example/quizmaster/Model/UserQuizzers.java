package com.example.quizmaster.Model;

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
}
