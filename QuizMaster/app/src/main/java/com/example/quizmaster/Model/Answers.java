package com.example.quizmaster.Model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class Answers {
    @SerializedName("answer_id")
    private Long answer_id;
    @SerializedName("user_id")
    private Users user_id;
    @SerializedName("quizzer_id")
    private Quizzers quizzer_id;
    @SerializedName("question_id")
    private Questions question_id;
}
