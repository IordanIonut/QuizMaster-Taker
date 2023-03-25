package com.example.quizmaster.Model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Courses {
    @SerializedName("course_id")
    private Long course_id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("user_id")
    private Users user_id;
    public Courses(String name, String description, Users user_id){
        this.name = name;
        this.description = description;
        this.user_id = user_id;
    }
    public Courses(){

    }
}
