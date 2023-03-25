package com.example.quizmaster.Model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class UserCourses {
    @SerializedName("user_course_id")
    private Long user_course_id;
    @SerializedName("user_id")
    private Users user_id;
    @SerializedName("course_id")
    private Courses course_id;
}
