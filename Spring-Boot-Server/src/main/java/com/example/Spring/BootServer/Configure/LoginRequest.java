package com.example.Spring.BootServer.Configure;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
public class LoginRequest {
    @NotBlank(message = "Email address is mandatory!")
    @Email(message = "The email address is invalid!")
    private String email;
    @NotBlank(message = "The password is mandatory!")
    private String password;
    @NotBlank(message = "The role is mandatory!")
    private String role;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
