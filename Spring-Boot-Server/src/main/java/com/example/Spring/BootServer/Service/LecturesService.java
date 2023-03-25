package com.example.Spring.BootServer.Service;

import com.example.Spring.BootServer.Model.Courses;
import com.example.Spring.BootServer.Model.Lectures;
import com.example.Spring.BootServer.Repository.CoursesRepository;
import com.example.Spring.BootServer.Repository.LecturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LecturesService {
    @Autowired
    private LecturesRepository lecturesRepository;
    public List<Lectures> getAllLectures() {
        return lecturesRepository.findAll();
    }
    public Lectures addLecture(Lectures quizzers) {
        return lecturesRepository.save(quizzers);
    }
    public Lectures updateLecture(Lectures userCourse) {
        Lectures existingUser = lecturesRepository.findById(userCourse.getLecture_id()).orElse(null);
        if (existingUser == null) {
            return null;
        }
        return lecturesRepository.save(userCourse);
    }
    public void deleteLecture(Long id) {
        lecturesRepository.deleteById(id);
    }
    public Lectures lastLectureInsert(){
        return lecturesRepository.lastLectureInsert();
    }
    public List<Lectures> selectLecutreByCourseId(Long course_id){
        return lecturesRepository.selectLecutreByCourseId(course_id);
    }
    public Lectures selectLectureByLectureId(Long lecture_id){
        return lecturesRepository.selectLectureByLectureId(lecture_id);
    }
    @Transactional
    public void deleteLecturesByLectureId(Long lecture_id){
        lecturesRepository.deleteLecturesByLectureId(lecture_id);
    }
}
