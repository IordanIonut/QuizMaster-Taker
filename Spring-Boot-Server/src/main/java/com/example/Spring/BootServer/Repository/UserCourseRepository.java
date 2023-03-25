package com.example.Spring.BootServer.Repository;

import com.example.Spring.BootServer.Model.UserCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface UserCourseRepository extends JpaRepository<UserCourses,Long> {
    @Query(value = "SELECT * FROM users_courses where user_id = :user_id and course_id = :course_id", nativeQuery = true)
    UserCourses selectUserCourseByUserIdAndCourseId(@Param("user_id") Long user_id, @Param("course_id") Long course_id);
    @Query(value = "SELECT * FROM users_courses where user_id = :user_id order by user_course_id desc", nativeQuery = true)
    List<UserCourses> selectAllUserCoursesByUserId(@Param("user_id") Long user_id);
    @Query(value = "select * from users_courses as uc inner join courses as c on uc.course_id = c.course_id where c.user_id = :user_id order by user_course_id desc", nativeQuery = true)
    List<UserCourses> selecltAllUsersCoursesAbonmentioned(@Param("user_id") Long user_id);
}
