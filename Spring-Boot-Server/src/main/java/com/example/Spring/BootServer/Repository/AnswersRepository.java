package com.example.Spring.BootServer.Repository;

import com.example.Spring.BootServer.Model.Answers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.Spring.BootServer.Model.UserCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AnswersRepository  extends JpaRepository<Answers,Long> {
    @Query(value = "SELECT a.question_id, " +
            "SUM(CASE WHEN a.answer = 1 THEN 1 ELSE 0 END) AS count_answer_1, " +
            "SUM(CASE WHEN a.answer = 0 THEN 1 ELSE 0 END) AS count_answer_0 " +
            "FROM answers a " +
            "WHERE a.quizzer_id = :quizzer_id " +
            "GROUP BY a.question_id" , nativeQuery = true)
    List<Object[]> countAnswersByQuestionIdAndAnswer(@Param("quizzer_id") Long quizzer_id);
    @Query(value = "SELECT qui.quizzer_id, COUNT(qui.quizzer_id) AS quizzer_count FROM questions AS que INNER JOIN quizzers AS qui ON " +
            "que.quizzer_id = qui.quizzer_id INNER JOIN lectures AS lec ON qui.lecture_id = lec.lecture_id INNER JOIN courses AS cou ON " +
            "cou.course_id = lec.course_id WHERE cou.course_id = :course_id GROUP BY qui.quizzer_id", nativeQuery = true)
    List<Object[]> countLenghtQuizzer(@Param("course_id") Long course_id);
}
