package com.example.Spring.BootServer.Controller;

import com.example.Spring.BootServer.Model.Courses;
import com.example.Spring.BootServer.Model.Lectures;
import com.example.Spring.BootServer.Service.CoursesService;
import com.example.Spring.BootServer.Service.LecturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class LecturesController {
    @Autowired
    private LecturesService lectureService;
    @GetMapping("/lectures/all")
    public List<Lectures> getAllCourses() {
        return lectureService.getAllLectures();
    }
    @PostMapping("/lectures/add")
    public Lectures addCourse(@RequestBody Lectures user) {
        Lectures savedContent = lectureService.addLecture(user);
        return new ResponseEntity<>(savedContent, HttpStatus.CREATED).getBody();
    }
    @PutMapping("/lectures/update")
    public Lectures updateCourse(@RequestBody Lectures user) {
        return lectureService.updateLecture(user);
    }
    @DeleteMapping("/lectures/delete/{id}")
    public void deleteCourse(@PathVariable Long id) {
        lectureService.deleteLecture(id);
    }
    @GetMapping("/lectures/last")
    public Lectures lastCourseInset(){
        return lectureService.lastLectureInsert();
    }
    @GetMapping("/lectures/course_id")
    public List<Lectures> selectLecutreByCourseId(@RequestParam("course_id") Long course_id){
        return lectureService.selectLecutreByCourseId(course_id);
    }
    @GetMapping("/lectures/lecture_id")
    public Lectures selectLectureByLectureId(@RequestParam("lecture_id") Long lecture_id){
        return lectureService.selectLectureByLectureId(lecture_id);
    }
    @Transactional
    @DeleteMapping("/lectures/delete/lecture_id")
    public void deleteLecturesByLectureId(@RequestParam("lecture_id")Long lecture_id) {
        lectureService.deleteLecturesByLectureId(lecture_id);
    }
}
