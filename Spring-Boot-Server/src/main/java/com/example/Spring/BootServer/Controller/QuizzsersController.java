package com.example.Spring.BootServer.Controller;

import com.example.Spring.BootServer.Model.Quizzers;
import com.example.Spring.BootServer.Model.UserCourses;
import com.example.Spring.BootServer.Repository.QuizzersRepository;
import com.example.Spring.BootServer.Service.QuizzersService;
import com.example.Spring.BootServer.Service.UserCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class QuizzsersController {
    @Autowired
    private QuizzersService quizzersService;
    @GetMapping("/quizzers/all")
    public List<Quizzers> getAllQuizzers() {
        return quizzersService.getAllQuizzers();
    }
    @PostMapping("/quizzers/add")
    public Quizzers addQuizzers(@RequestBody Quizzers user) {
        Quizzers savedContent = quizzersService.addQuizzers(user);
        return new ResponseEntity<>(savedContent, HttpStatus.CREATED).getBody();
    }
    @PutMapping("/quizzers/update")
    public Quizzers updateQuizzers(@RequestBody Quizzers user) {
        return quizzersService.updateQuizzers(user);
    }
    @DeleteMapping("/quizzers/delete/{id}")
    public void deleteQuizzers(@PathVariable Long id) {
        quizzersService.deleteQuizzers(id);
    }
    @GetMapping("/quizzers/last")
    public Quizzers lastQuizzerInsert(){
        return quizzersService.lastQuizzerInsert();
    }
    @GetMapping("/quizzers/lecture_id")
    public Quizzers selectByLectureId(@RequestParam("lecture_id") Long lecture_id){
        return quizzersService.selectByLectureId(lecture_id);
    }
}
