package com.example.Spring.BootServer.Service;

import com.example.Spring.BootServer.Model.UserCourses;
import com.example.Spring.BootServer.Model.UserQuizzes;
import com.example.Spring.BootServer.Repository.UserCourseRepository;
import com.example.Spring.BootServer.Repository.UserQuizzersRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCoursesService {
    @Autowired
    private UserCourseRepository userCourseRepository;
    public List<UserCourses> getAllUsersCourses() {
        return userCourseRepository.findAll();
    }
    public UserCourses addUserCourse(UserCourses userCourse) {
        return userCourseRepository.save(userCourse);
    }
    public UserCourses updateUserCourses(UserCourses userCourse) {
        UserCourses existingUser = userCourseRepository.findById(userCourse.getUser_course_id()).orElse(null);
        if (existingUser == null) {
            return null;
        }
        return userCourseRepository.save(userCourse);
    }
    public void deleteUserCourses(Long id) {
        userCourseRepository.deleteById(id);
    }
    public UserCourses selectUserCourseByUserIdAndCourseId(Long user_id, Long course_id) {
        return userCourseRepository.selectUserCourseByUserIdAndCourseId(user_id, course_id);
    }
    public List<UserCourses> selectAllUserCoursesByUserId(Long user_id){
        return userCourseRepository.selectAllUserCoursesByUserId(user_id);
    }
    public List<UserCourses> selecltAllUsersCoursesAbonmentioned(Long user_id){
        return userCourseRepository.selecltAllUsersCoursesAbonmentioned(user_id);
    }
}
