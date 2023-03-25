package com.example.Spring.BootServer.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "questions")
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long question_id;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "quizzer_id", referencedColumnName = "quizzer_id")
    private Quizzers quizzer_id;
    @Column(name = "question")
    private String question;
    @Column(name = "answer_1")
    private String answer_1;
    @Column(name = "answer_2")
    private String answer_2;
    @Column(name = "answer_3")
    private String answer_3;
    @Column(name = "answer_4")
    private String answer_4;
    @Column(name = "correct_answer")
    private String correct_answer;
    public Questions() {
    }
}
