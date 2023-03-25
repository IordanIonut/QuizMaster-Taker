package com.example.Spring.BootServer.Repository;

import com.example.Spring.BootServer.Model.Lectures;
import com.example.Spring.BootServer.Model.Quizzers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LecturesRepository extends JpaRepository<Lectures,Long> {
    @Query(value = "SELECT * FROM lectures  ORDER BY lecture_id DESC LIMIT 1;", nativeQuery = true)
    Lectures lastLectureInsert();
    @Query(value = "SELECT * FROM lectures WHERE course_id = :course_id", nativeQuery = true)
    List<Lectures> selectLecutreByCourseId(@Param("course_id") Long course_id);
    @Query(value = "SELECT * FROM lectures WHERE lecture_id = :lecture_id", nativeQuery = true)
    Lectures selectLectureByLectureId(@Param("lecture_id") Long lecture_id);
    @Modifying
    @Query(value = "DELETE FROM lectures WHERE lecture_id = :lecture_id", nativeQuery = true)
    void deleteLecturesByLectureId(@Param("lecture_id") Long lecture_id);

}
