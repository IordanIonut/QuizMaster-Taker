package com.example.Spring.BootServer.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users_courses")
public class UserCourses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_course_id")
    private Long user_course_id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user_id;
    @OneToOne(cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Courses course_id;
    public UserCourses() {
    }
}
