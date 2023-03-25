package com.example.quizmaster.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
    private String role;
    public LoginRequest() {
    }
}
