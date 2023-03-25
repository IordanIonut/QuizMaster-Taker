package com.example.quizmaster.Model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Users {
    @SerializedName("user_id")
    private Long user_id;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("phone_number")
    private String phone_number;
    @SerializedName("role")
    private String role;
    @SerializedName("user_name")
    private String user_name;
    public Users() {
    }
    public Users(String email, String password, String phone_number, String role, String user_name) {
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.role = role;
        this.user_name = user_name;
    }

}
