package com.example.Spring.BootServer.Repository;

import com.example.Spring.BootServer.Model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface QuestionsRepository  extends JpaRepository<Questions,Long> {
    @Query(value = "SELECT * FROM questions ORDER BY question_id DESC LIMIT 1;", nativeQuery = true)
    Questions lastQuestionInsert();
    @Query(value = "SELECT * FROM questions WHERE quizzer_id = :quizzer_id", nativeQuery = true)
    List<Questions> selectByQuizzerId(@Param("quizzer_id") Long quizzer_id);
}
