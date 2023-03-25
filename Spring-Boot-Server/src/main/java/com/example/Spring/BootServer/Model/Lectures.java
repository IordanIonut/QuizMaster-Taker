package com.example.Spring.BootServer.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lectures")
public class Lectures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long lecture_id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name="lecture")
    @Size(max=11500)
    private String lecture;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Courses course_id;
    public Lectures() {
    }
}

