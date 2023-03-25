package com.example.Spring.BootServer.Controller;

import com.example.Spring.BootServer.Model.UserQuizzes;
import com.example.Spring.BootServer.Model.Users;
import com.example.Spring.BootServer.Service.UserQuizzersService;
import com.example.Spring.BootServer.Service.UsersService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserQuizzersController {
    @Autowired
    private UserQuizzersService usersQuizzerService;
    @GetMapping("/usersQuizzers/all")
    public List<UserQuizzes> getAllUsersQuizzes() {
        return usersQuizzerService.getAllUsersQuizzes();
    }
    @PostMapping("/usersQuizzers/add")
    public UserQuizzes addUserQuizzes(@RequestBody UserQuizzes user) {
        UserQuizzes savedContent = usersQuizzerService.addUserQuizzes(user);
        return new ResponseEntity<>(savedContent, HttpStatus.CREATED).getBody();
    }
    @PutMapping("/usersQuizzers/update")
    public UserQuizzes updateUserQuizzes(@RequestBody UserQuizzes user) {
        return usersQuizzerService.updateUserQuizzes(user);
    }
    @DeleteMapping("/usersQuizzers/delete/{id}")
    public void deleteUserQuzzers(@PathVariable Long id) {
        usersQuizzerService.deleteUserQuzzers(id);
    }
    @GetMapping("/usersQuizzers/user_id/quizzer_id")
    public UserQuizzes selectUserQuizzesByUserIdAndQuizzerId(@RequestParam("user_id") Long user_id, @RequestParam("quizzer_id") Long quizzer_id){
        return  usersQuizzerService.selectUserQuizzesByUserIdAndQuizzerId(user_id,quizzer_id);
    }
    @GetMapping("/usersQuizzers/course_id")
    public List<UserQuizzes> selectUserQuizzesFromCourseId(@RequestParam("course_id") Long course_id){
        return usersQuizzerService.selectUserQuizzesFromCourseId(course_id);
    }
}
