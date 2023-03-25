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
@Table(name = "answers")
public class Answers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answer_id;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user_id;
    @OneToOne(cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JoinColumn(name = "quizzer_id", referencedColumnName = "quizzer_id")
    private Quizzers quizzer_id;
    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private Questions question_id;
    @Column(name = "answer")
    private Boolean answer;
    public Answers() {
    }
}
