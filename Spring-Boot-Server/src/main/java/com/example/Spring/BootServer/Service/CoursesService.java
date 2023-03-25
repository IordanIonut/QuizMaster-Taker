package com.example.Spring.BootServer.Service;

import com.example.Spring.BootServer.Model.Courses;
import com.example.Spring.BootServer.Model.Questions;
import com.example.Spring.BootServer.Repository.CoursesRepository;
import com.example.Spring.BootServer.Repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CoursesService {
    @Autowired
    private CoursesRepository coursesRepository;
    public List<Courses> getAllCourses() {
        return coursesRepository.findAll();
    }
    public Courses addCourse(Courses quizzers) {
        return coursesRepository.save(quizzers);
    }
    public Courses updateCourse(Courses userCourse) {
        Courses existingUser = coursesRepository.findById(userCourse.getCourse_id()).orElse(null);
        if (existingUser == null) {
            return null;
        }
        return coursesRepository.save(userCourse);
    }
    public void deleteCourse(Long id) {
        coursesRepository.deleteById(id);
    }
    public Courses lastCourseInset(){
        return coursesRepository.lastCourseInset();
    }
    public List<Courses> selectByNameAndDescription(String description, String name, Long user_id){
        return coursesRepository.selectByNameAndDescription(description,name, user_id);
    }
    public List<Courses> selectLast6CoursesByUserId(Long user_id){
        return coursesRepository.selectLast6CoursesByUserId(user_id);
    }
    public List<Courses> selectAllCoursesByUserId(Long user_id){
        return coursesRepository.selectAllCoursesByUserId(user_id);
    }
    public Courses selectCourseByCourseId(Long course_id){
        return coursesRepository.selectCourseByCourseId(course_id);
    }
    public List<Courses> selecltAllCoursesDesc(){
        return coursesRepository.selecltAllCoursesDesc();
    }
    @Transactional
    public void deleteCoursesByCourseId(Long course_id){
        coursesRepository.deleteCoursesByCourseId(course_id);
    }
    public Object[] selectLecturesCounter(Long course_id){
        return coursesRepository.selectLecturesCounter(course_id);
    }
    public List<Courses> selectCoursesByName(String name, Long user_id){
        return coursesRepository.selectCoursesByName(name, user_id);
    }
    public List<Courses> selectCursusesComplete(Long user_id, String name){
        return coursesRepository.selectCursusesComplete(user_id, name);
    }
    public List<Courses> selectCoursesNotCompletedName(Long user_id, String name){
        return coursesRepository.selectCoursesNotCompletedName(user_id, name);
    }
    public List<Courses> selectCourseStarted(Long user_id){
        return coursesRepository.selectCourseStarted(user_id);
    }
    public List<Courses> selectCoursesNotAbonament(Long user_id, String name){
        return coursesRepository.selectCoursesNotAbonament(user_id, name);
    }
}
