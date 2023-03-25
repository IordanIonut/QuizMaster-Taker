package com.example.Spring.BootServer.Controller;

import com.example.Spring.BootServer.Model.Courses;
import com.example.Spring.BootServer.Model.Questions;
import com.example.Spring.BootServer.Service.CoursesService;
import com.example.Spring.BootServer.Service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CoursesController {
    @Autowired
    private CoursesService coursesService;
    @GetMapping("/courses/all")
    public List<Courses> getAllCourses() {
        return coursesService.getAllCourses();
    }
    @PostMapping("/courses/add")
    public Courses addCourse(@RequestBody Courses user) {
        Courses savedContent = coursesService.addCourse(user);
        return new ResponseEntity<>(savedContent, HttpStatus.CREATED).getBody();
    }
    @PutMapping("/courses/update")
    public Courses updateCourse(@RequestBody Courses user) {
        return coursesService.updateCourse(user);
    }
    @DeleteMapping("/courses/delete/{id}")
    public void deleteCourse(@PathVariable Long id) {
        coursesService.deleteCourse(id);
    }
    @GetMapping("/courses/last")
    public Courses lastCourseInset(){
        return  coursesService.lastCourseInset();
    }
    @GetMapping("/courses/name/description")
    public List<Courses> selectByNameAndDescription(@RequestParam("description") String description, @RequestParam("name") String name, @RequestParam("user_id") Long user_id){
        return coursesService.selectByNameAndDescription(description, name, user_id);
    }
    @GetMapping("/courses/last/6/user_id")
    public List<Courses> selectLast6CoursesByUserId(@RequestParam("user_id") Long user_id){
        return coursesService.selectLast6CoursesByUserId(user_id);
    }
    @GetMapping("/courses/all/user_id")
    public List<Courses> selectAllCoursesByUserId(@RequestParam("user_id") Long user_id){
        return coursesService.selectAllCoursesByUserId(user_id);
    }
    @GetMapping("/courses/course_id")
    public Courses selectCourseByCourseId(@RequestParam("course_id") Long course_id){
        return coursesService.selectCourseByCourseId(course_id);
    }
    @GetMapping("/courses/all/desc")
    public List<Courses> selecltAllCoursesDesc(){
        return coursesService.selecltAllCoursesDesc();
    }
    @Transactional
    @DeleteMapping("/courses/delete/course_id")
    public void deleteCoursesByCourseId(@RequestParam("course_id")Long course_id){
        coursesService.deleteCoursesByCourseId(course_id);
    }
    @GetMapping("/courses/count/course_id")
    public Object[] selectLecturesCounter(@RequestParam("course_id")Long course_id){
        return coursesService.selectLecturesCounter(course_id);
    }
    @GetMapping("/courses/name")
    public List<Courses> selectCoursesByName(@RequestParam("name") String name, @RequestParam("user_id") Long user_id){
        return coursesService.selectCoursesByName(name, user_id);
    }
    @GetMapping("/courses/complete/user_id/name")
    public List<Courses> selectCursusesComplete(@RequestParam("user_id") Long user_id, @RequestParam("name") String name){
        return coursesService.selectCursusesComplete(user_id, name);
    }
    @GetMapping("/courses/not/complete/user_id/name")
    public List<Courses> selectCoursesNotCompletedName(@RequestParam("user_id") Long user_id, @RequestParam("name") String name) {
        return coursesService.selectCoursesNotCompletedName(user_id, name);
    }
    @GetMapping("/courses/started/user_id")
    public List<Courses> selectCourseStarted(@RequestParam("user_id") Long user_id){
        return coursesService.selectCourseStarted(user_id);
    }
    @GetMapping("/courses/not/abonament/user_id/name")
    public List<Courses> selectCoursesNotAbonament(@RequestParam("user_id") Long user_id, @RequestParam("name") String name){
        return coursesService.selectCoursesNotAbonament(user_id,name);
    }
}
