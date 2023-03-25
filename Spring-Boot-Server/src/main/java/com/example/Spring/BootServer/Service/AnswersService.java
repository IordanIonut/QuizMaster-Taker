package com.example.Spring.BootServer.Service;

import com.example.Spring.BootServer.Model.Answers;
import com.example.Spring.BootServer.Model.Courses;
import com.example.Spring.BootServer.Repository.AnswersRepository;
import com.example.Spring.BootServer.Repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class AnswersService {
    @Autowired
    private AnswersRepository answersRepository;
    public List<Answers> getAnswers() {
        return answersRepository.findAll();
    }
    public Answers addAnswers(Answers answers) {
        return answersRepository.save(answers);
    }
    public Answers updateAnswers(Answers answers){
        Answers existingUser = answersRepository.findById(answers.getAnswer_id()).orElse(null);
        if (existingUser == null) {
            return null;
        }
        return answersRepository.save(answers);
    }
    public void deleteAnswers(Long id) {
        answersRepository.deleteById(id);
    }
    public List<Object[]> countAnswersByQuestionIdAndAnswer(Long quizzerId) {
        return answersRepository.countAnswersByQuestionIdAndAnswer(quizzerId);
    }
    public List<Object[]> countLenghtQuizzer(Long course_id){
        return answersRepository.countLenghtQuizzer(course_id);
    }
}
