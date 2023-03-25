package com.example.quiztaker.Model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class Questions {
    @SerializedName("question_id")
    private Long question_id;
    @SerializedName("quizzer_id")
    private Quizzers quizzer_id;
    @SerializedName("question")
    private String question;
    @SerializedName("answer_1")
    private String answer_1;
    @SerializedName("answer_2")
    private String answer_2;
    @SerializedName("answer_3")
    private String answer_3;
    @SerializedName("answer_4")
    private String answer_4;
    @SerializedName("correct_answer")
    private String correct_answer;
    public Questions(Quizzers quizzer_id, String question, String correct_answer, String answer_1, String answer_2, String answer_3, String answer_4) {
        this.quizzer_id = quizzer_id;
        this.question = question;
        this.answer_1 = answer_1;
        this.answer_2 = answer_2;
        this.answer_3 = answer_3;
        this.answer_4 = answer_4;
        this.correct_answer = correct_answer;
    }
}
