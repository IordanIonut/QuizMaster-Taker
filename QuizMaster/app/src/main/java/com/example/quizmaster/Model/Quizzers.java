package com.example.quizmaster.Model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class Quizzers {
    @SerializedName("quizzer_id")
    private Long quizzer_id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("lecture_id")
    private Lectures lecture_id;
    public Quizzers(String name, String description, Lectures lecture_id) {
        this.name = name;
        this.description = description;
        this.lecture_id = lecture_id;
    }
}
