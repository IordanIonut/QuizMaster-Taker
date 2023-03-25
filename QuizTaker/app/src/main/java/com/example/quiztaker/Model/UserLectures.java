package com.example.quiztaker.Model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class UserLectures {
    @SerializedName("user_lecture_id")
    private Long user_lecture_id;
    @SerializedName("user_id")
    private Users user_id;
    @SerializedName("lecture_id")
    private Lectures lecture_id;

    public UserLectures(Users user_id, Lectures lecture_id) {
        this.user_id = user_id;
        this.lecture_id = lecture_id;
    }
}
