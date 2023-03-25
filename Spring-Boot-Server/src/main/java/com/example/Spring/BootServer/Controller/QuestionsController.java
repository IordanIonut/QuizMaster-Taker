package com.example.Spring.BootServer.Controller;

import com.example.Spring.BootServer.Model.Questions;
import com.example.Spring.BootServer.Service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class QuestionsController {
    @Autowired
    private QuestionsService questionsService;
    @GetMapping("/questions/all")
    public List<Questions> getAllQuestions() {
        return questionsService.getAllQuestions();
    }
    @PostMapping("/questions/add")
    public Questions addQuestions(@RequestBody Questions user) {
        Questions savedContent = questionsService.addQuestions(user);
        return new ResponseEntity<>(savedContent, HttpStatus.CREATED).getBody();
    }
    @PutMapping("/questions/update")
    public Questions updateQuestions(@RequestBody Questions user) {
        return questionsService.updateQuestions(user);
    }
    @DeleteMapping("/questions/delete/{id}")
    public void deleteQuestions(@PathVariable Long id) {
        questionsService.deleteQuestions(id);
    }
    @GetMapping("/questions/last")
    public Questions lastQuestionInsert(){
        return questionsService.lastQuestionInsert();
    }
    @GetMapping("/questions/quizzer_id")
    public List<Questions> selectByQuizzerId(@RequestParam("quizzer_id") Long quizzer_id){
        return questionsService.selectByQuizzerId(quizzer_id);
    }

}
