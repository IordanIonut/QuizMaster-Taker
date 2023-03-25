package com.example.Spring.BootServer.Controller;

import com.example.Spring.BootServer.Model.Lectures;
import com.example.Spring.BootServer.Model.UserLectures;
import com.example.Spring.BootServer.Service.LecturesService;
import com.example.Spring.BootServer.Service.UserLecturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserLecturesController {
    @Autowired
    private UserLecturesService userLecturesService;
    @GetMapping("/userLectures/all")
    public List<UserLectures> getAllCourses() {
        return userLecturesService.getUserLectures();
    }
    @PostMapping("/userLectures/add")
    public UserLectures addCourse(@RequestBody UserLectures user) {
        UserLectures savedContent = userLecturesService.addUserLectures(user);
        return new ResponseEntity<>(savedContent, HttpStatus.CREATED).getBody();
    }
    @PutMapping("/userLectures/update")
    public UserLectures updateCourse(@RequestBody UserLectures user) {
        return userLecturesService.updateUserLectures(user);
    }
    @DeleteMapping("/userLectures/delete/{id}")
    public void deleteCourse(@PathVariable Long id) {
        userLecturesService.deleteUserLecutres(id);
    }
    @GetMapping("/userLectures/user_id/lecture_id")
    public UserLectures selectUserLecturesByUserIdAndLectureId(@RequestParam("user_id") Long userId, @RequestParam("lecture_id") Long lecture_id){
        return  userLecturesService.selectUserLecturesByUserIdAndLectureId(userId, lecture_id);
    }
    @GetMapping("/userLectures/user_id")
    public List<UserLectures> selectUserLecturesByUserId(@RequestParam("user_id") Long user_id){
        return userLecturesService.selectUserLecturesByUserId(user_id);
    }
}
