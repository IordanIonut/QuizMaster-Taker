package com.example.Spring.BootServer.Service;

import com.example.Spring.BootServer.Model.Quizzers;
import com.example.Spring.BootServer.Model.UserCourses;
import com.example.Spring.BootServer.Repository.QuizzersRepository;
import com.example.Spring.BootServer.Repository.UserCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizzersService {
    @Autowired
    private QuizzersRepository quizzersRepository;
    public List<Quizzers> getAllQuizzers() {
        return quizzersRepository.findAll();
    }
    public Quizzers addQuizzers(Quizzers quizzers) {
        return quizzersRepository.save(quizzers);
    }
    public Quizzers updateQuizzers(Quizzers userCourse) {
        Quizzers existingUser = quizzersRepository.findById(userCourse.getQuizzer_id()).orElse(null);
        if (existingUser == null) {
            return null;
        }
        return quizzersRepository.save(userCourse);
    }
    public void deleteQuizzers(Long id) {
        quizzersRepository.deleteById(id);
    }
    public Quizzers lastQuizzerInsert() {
       return quizzersRepository.lastQuizzerInsert();
    }
    public Quizzers selectByLectureId(Long lecture_id){
        return quizzersRepository.selectByLectureId(lecture_id);
    }
}
