package com.example.Spring.BootServer.Repository;

import com.example.Spring.BootServer.Model.Quizzers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface  QuizzersRepository  extends JpaRepository<Quizzers,Long> {
    @Query(value = "SELECT * FROM quizzers  ORDER BY quizzer_id DESC LIMIT 1;", nativeQuery = true)
    Quizzers lastQuizzerInsert();
    @Query(value = "SELECT * FROM quizzers WHERE lecture_id = :lecture_id", nativeQuery = true)
    Quizzers selectByLectureId(@Param("lecture_id") Long lecture_id);
}
