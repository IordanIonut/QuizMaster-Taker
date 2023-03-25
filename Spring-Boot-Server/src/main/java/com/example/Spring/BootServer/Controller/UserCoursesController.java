package com.example.Spring.BootServer.Controller;

import com.example.Spring.BootServer.Model.UserCourses;
import com.example.Spring.BootServer.Model.UserQuizzes;
import com.example.Spring.BootServer.Service.UserCoursesService;
import com.example.Spring.BootServer.Service.UserQuizzersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserCoursesController {
    @Autowired
    private UserCoursesService userCoursesService;
    @GetMapping("/usersCourses/all")
    public List<UserCourses> getAllUsersCourses() {
        return userCoursesService.getAllUsersCourses();
    }
    @PostMapping("/usersCourses/add")
    public UserCourses addUserCourse(@RequestBody UserCourses user) {
        UserCourses savedContent = userCoursesService.addUserCourse(user);
        return new ResponseEntity<>(savedContent, HttpStatus.CREATED).getBody();
    }
    @PutMapping("/usersCourses/update")
    public UserCourses updateUserCourses(@RequestBody UserCourses user) {
        return userCoursesService.updateUserCourses(user);
    }
    @DeleteMapping("/usersCourses/delete/{id}")
    public void deleteUserCourses(@PathVariable Long id) {
        userCoursesService.deleteUserCourses(id);
    }
    @GetMapping("/usersCourses/user_id/course_id")
    public UserCourses selectUserCourseByUserIdAndCourseId(@RequestParam("user_id") Long user_id, @RequestParam("course_id") Long course_id){
        return userCoursesService.selectUserCourseByUserIdAndCourseId(user_id, course_id);
    }
    @GetMapping("/usersCourses/all/user_id")
    public List<UserCourses> selectAllUserCoursesByUserId(@RequestParam("user_id") Long user_id){
        return userCoursesService.selectAllUserCoursesByUserId(user_id);
    }
    @GetMapping("/usersCourses/all/abonamentioned/user_id")
    public List<UserCourses> selecltAllUsersCoursesAbonmentioned(@RequestParam("user_id") Long user_id){
        return userCoursesService.selecltAllUsersCoursesAbonmentioned(user_id);
    }
}
