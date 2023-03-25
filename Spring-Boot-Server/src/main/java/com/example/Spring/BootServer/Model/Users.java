package com.example.Spring.BootServer.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;
    @NotBlank(message = "The name is mandatory!")
    @Size(min = 6, message = "The name needs to have minimum 6 characters!")
    @Column(name = "user_name")
    private String user_name;
    @NotBlank(message = "The password is mandatory!")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.*[a-zA-Z]).{8,}$", message = "The password must contain at least one digit and a special character (!,@,#,$,%,&,*,(,),-_,+={},[],|,;,:,\\\",',,,.,/ or ?) and have at least 8 characters!")
    @Column(name = "password")
    @Size(min = 8, message = "The password needs to have minimum 8 characters!")
    private String password;
    @NotBlank(message = "Email address is mandatory!")
    @Email(message = "The email address is invalid!")
    @Column(name = "email", unique = true)
    private String email;
    @Size(min = 10, max = 12, message = "Phone number should be between 10 and 12 digits!")
    @Column(name = "phone_number", unique = true)
    private String phone_number;
    @Column(name ="role")
    private String role;
    public Users() {
    }
}
