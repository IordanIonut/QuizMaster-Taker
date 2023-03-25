package com.example.Spring.BootServer.Repository;

import com.example.Spring.BootServer.Model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CoursesRepository  extends JpaRepository<Courses,Long> {
    @Query(value = "SELECT * FROM courses ORDER BY course_id DESC LIMIT 1;", nativeQuery = true)
    Courses lastCourseInset();
    @Query(value = "SELECT * FROM courses WHERE description = :description and name= :name and user_id = :user_id order by course_id desc;", nativeQuery = true)
    List<Courses> selectByNameAndDescription(@Param("description") String description, @Param("name") String name, @Param("user_id") Long user_id);
    @Query(value = "SELECT * FROM courses WHERE user_id = :user_id ORDER BY course_id DESC LIMIT 6", nativeQuery = true)
    List<Courses> selectLast6CoursesByUserId(@Param("user_id") Long user_id);
    @Query(value = "SELECT * FROM courses WHERE user_id = :user_id ORDER BY course_id DESC" , nativeQuery = true)
    List<Courses> selectAllCoursesByUserId(@Param("user_id") Long user_id);
    @Query(value = "SELECT * FROM courses WHERE course_id = :course_id", nativeQuery = true)
    Courses selectCourseByCourseId(@Param("course_id") Long course_id);
    @Query(value = "SELECT * FROM courses ORDER BY course_id DESC ", nativeQuery = true)
    List<Courses> selecltAllCoursesDesc();
    @Modifying
    @Query(value = "DELETE FROM courses WHERE course_id = :course_id", nativeQuery = true)
    void deleteCoursesByCourseId(@Param("course_id") Long course_id);
    @Query(value = "SELECT COUNT(*) FROM lectures WHERE course_id = :course_id", nativeQuery = true)
    Object[] selectLecturesCounter(@Param("course_id") Long course_id);
    @Query(value = "SELECT * from courses where name like %:name% and user_id = :user_id order by course_id desc", nativeQuery = true)
    List<Courses> selectCoursesByName(@Param("name") String name, @Param("user_id") Long user_id);
    @Query(value = "SELECT * FROM courses AS c JOIN (SELECT lec.course_id, COUNT(lec.lecture_id) AS lecture_count FROM lectures AS lec GROUP BY " +
            "lec.course_id) AS lc ON c.course_id = lc.course_id JOIN (SELECT l.course_id, COUNT(*) AS user_lecture_count FROM users_lectures AS ul " +
            "INNER JOIN lectures AS l ON ul.lecture_id = l.lecture_id WHERE user_id = :user_id GROUP BY l.course_id) AS uc ON " +
            "c.course_id = uc.course_id WHERE lc.lecture_count = uc.user_lecture_count AND name LIKE %:name% ORDER BY c.name", nativeQuery = true)
    List<Courses> selectCursusesComplete(@Param("user_id") Long user_id, @Param("name") String name);
    @Query(value="SELECT c.* FROM courses AS c JOIN (SELECT lec.course_id, COUNT(lec.lecture_id) AS lecture_count FROM lectures AS lec GROUP BY lec.course_id) AS lc ON " +
            "c.course_id = lc.course_id JOIN (SELECT uc.course_id, COUNT(ul.lecture_id) AS user_lecture_count FROM users_courses AS uc LEFT JOIN " +
            "lectures AS lec ON uc.course_id = lec.course_id LEFT JOIN users_lectures AS ul ON lec.lecture_id = ul.lecture_id AND ul.user_id = :user_id " +
            "WHERE uc.user_id = :user_id GROUP BY uc.course_id) AS uc ON c.course_id = uc.course_id WHERE ((lc.lecture_count <> uc.user_lecture_count OR " +
            "uc.user_lecture_count IS NULL) OR (lc.lecture_count = 0 AND uc.user_lecture_count = 0)) AND name like %:name% ORDER BY c.name", nativeQuery = true)
    List<Courses> selectCoursesNotCompletedName(@Param("user_id") Long user_id, @Param("name") String name);
    @Query(value = "SELECT c.* FROM courses AS c JOIN (SELECT lec.course_id, COUNT(lec.lecture_id) AS lecture_count FROM lectures AS" +
            " lec GROUP BY lec.course_id) AS lc ON c.course_id = lc.course_id JOIN (SELECT l.course_id, COUNT(*) AS user_lecture_count FROM " +
            "users_lectures AS ul INNER JOIN lectures AS l ON ul.lecture_id = l.lecture_id WHERE user_id = :user_id GROUP BY l.course_id) AS uc ON " +
            "c.course_id = uc.course_id JOIN(SELECT * FROM users_courses WHERE user_id = :user_id) as ucc on uc.course_id = ucc.course_id WHERE " +
            "lc.lecture_count <> uc.user_lecture_count and ucc.course_id = uc.course_id OR uc.user_lecture_count IS NULL ORDER BY c.name", nativeQuery = true)
    List<Courses> selectCourseStarted(@Param("user_id") Long user_id);
    @Query(value = "SELECT * FROM courses WHERE course_id NOT IN (SELECT course_id FROM users_courses WHERE user_id = :user_id) AND name " +
            "LIKE %:name% ORDER BY name", nativeQuery = true)
    List<Courses> selectCoursesNotAbonament(@Param("user_id") Long user_id, @Param("name") String name);
}
