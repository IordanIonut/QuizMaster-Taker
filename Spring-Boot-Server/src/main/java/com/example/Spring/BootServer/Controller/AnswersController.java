package com.example.Spring.BootServer.Controller;

import com.example.Spring.BootServer.Model.Answers;
import com.example.Spring.BootServer.Model.Courses;
import com.example.Spring.BootServer.Service.AnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class AnswersController {
    @Autowired
    private AnswersService answersService;
    @GetMapping("/answers/all")
    public List<Answers> getAnswers() {
        return answersService.getAnswers();
    }
    @PostMapping("/answers/add")
    public Answers addAnswers(@RequestBody Answers user) {
        Answers savedContent = answersService.addAnswers(user);
        return new ResponseEntity<>(savedContent, HttpStatus.CREATED).getBody();
    }
    @PutMapping("/answers/update")
    public Answers updateAnswers(@RequestBody Answers user) {
        return answersService.updateAnswers(user);
    }
    @DeleteMapping("/answers/delete/{id}")
    public void deleteAnswers(@PathVariable Long id) {
        answersService.deleteAnswers(id);
    }
    @GetMapping("/answers/count/quizzer_id")
    public List<Object[]> countAnswersByQuestionIdAndAnswer(@RequestParam("quizzer_id") Long quizzer_id){
        return answersService.countAnswersByQuestionIdAndAnswer(quizzer_id);
    }
    @GetMapping("/answers/count/course_id")
    public List<Object[]> countLenghtQuizzer(@RequestParam("course_id") Long course_id){
        return answersService.countLenghtQuizzer(course_id);
    }
}
