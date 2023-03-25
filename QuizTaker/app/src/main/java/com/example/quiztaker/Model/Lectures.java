package com.example.quiztaker.Model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Lectures {
    @SerializedName("lecture_id")
    private Long lecture_id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("lecture")
    private String lecture;
    @SerializedName("course_id")
    private Courses course_id;
    public Lectures(String title, String description, String lecture, Courses course_id){
        this.title = title;
        this.description = description;
        this.lecture = lecture;
        this.course_id = course_id;
    }
}
