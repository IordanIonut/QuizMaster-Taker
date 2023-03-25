package com.example.quiztaker.Model;

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
    @SerializedName("answer")
    private Boolean answer;

    public Answers(Users user_id, Quizzers quizzer_id, Questions question_id, Boolean answer) {
        this.user_id = user_id;
        this.quizzer_id = quizzer_id;
        this.question_id = question_id;
        this.answer = answer;
    }
}
