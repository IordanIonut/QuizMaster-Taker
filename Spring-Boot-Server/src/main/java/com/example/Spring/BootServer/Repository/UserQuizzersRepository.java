package com.example.Spring.BootServer.Repository;

import org.springframework.data.repository.query.Param;
import com.example.Spring.BootServer.Model.UserQuizzes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface UserQuizzersRepository extends JpaRepository<UserQuizzes,Long> {
    @Query(value = "SELECT * FROM user_quizzes where user_id = :user_id and quizzer_id = :quizzer_id", nativeQuery = true)
    UserQuizzes selectUserQuizzesByUserIdAndQuizzerId(@Param(value = "user_id") Long user_id, @Param(value = "quizzer_id") Long quizzer_id);
    @Query(value = "select * from user_quizzes uq inner join quizzers q on uq.quizzer_id = q.quizzer_id inner join lectures l on " +
            "l.lecture_id = q.lecture_id inner join courses c on c.course_id = l.course_id where c.course_id = :course_id", nativeQuery = true)
    List<UserQuizzes> selectUserQuizzesFromCourseId(@Param("course_id") Long course_id);
}
