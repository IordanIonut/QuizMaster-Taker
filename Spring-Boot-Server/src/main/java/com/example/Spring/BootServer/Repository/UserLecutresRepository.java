package com.example.Spring.BootServer.Repository;

import com.example.Spring.BootServer.Model.UserLectures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLecutresRepository extends JpaRepository<UserLectures,Long> {
    @Query(value = "SELECT * FROM users_lectures where user_id = :user_id and lecture_id = :lecture_id", nativeQuery = true)
    UserLectures selectUserLecturesByUserIdAndLectureId(@Param(value = "user_id") Long user_id, @Param(value = "lecture_id") Long lecture_id);
    @Query(value = "select * from users_lectures where user_id = :user_id", nativeQuery = true)
    List<UserLectures> selectUserLecturesByUserId(@Param(value = "user_id") Long user_id);
}
