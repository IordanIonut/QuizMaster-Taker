package com.example.Spring.BootServer.Service;

import com.example.Spring.BootServer.Model.Questions;
import com.example.Spring.BootServer.Model.Quizzers;
import com.example.Spring.BootServer.Repository.QuestionsRepository;
import com.example.Spring.BootServer.Repository.QuizzersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class QuestionsService {
    @Autowired
    private QuestionsRepository questionsRepository;
    public List<Questions> getAllQuestions() {
        return questionsRepository.findAll();
    }
    public Questions addQuestions(Questions quizzers) {
        return questionsRepository.save(quizzers);
    }
    public Questions updateQuestions(Questions userCourse) {
        Questions existingUser = questionsRepository.findById(userCourse.getQuestion_id()).orElse(null);
        if (existingUser == null) {
            return null;
        }
        return questionsRepository.save(userCourse);
    }
    public void deleteQuestions(Long id) {
        questionsRepository.deleteById(id);
    }
    public Questions lastQuestionInsert(){
        return questionsRepository.lastQuestionInsert();
    }
    public List<Questions> selectByQuizzerId(Long quizzer_id){
        return questionsRepository.selectByQuizzerId(quizzer_id);
    }
}
