package com.example.Spring.BootServer.Service;

import com.example.Spring.BootServer.Model.UserQuizzes;
import com.example.Spring.BootServer.Repository.UserQuizzersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserQuizzersService {
    @Autowired
    private UserQuizzersRepository userQuizzersRepository;
    public List<UserQuizzes> getAllUsersQuizzes() {
        return userQuizzersRepository.findAll();
    }
    public UserQuizzes addUserQuizzes(UserQuizzes userQuizzes) {
        return userQuizzersRepository.save(userQuizzes);
    }
    public UserQuizzes updateUserQuizzes(UserQuizzes userQuizzes) {
        UserQuizzes existingUser = userQuizzersRepository.findById(userQuizzes.getUser_quizze_id()).orElse(null);
        if (existingUser == null) {
            return null;
        }
        return userQuizzersRepository.save(userQuizzes);
    }
    public void deleteUserQuzzers(Long id) {
        userQuizzersRepository.deleteById(id);
    }
    public UserQuizzes selectUserQuizzesByUserIdAndQuizzerId(Long user_id, Long quizzer_id){
        return userQuizzersRepository.selectUserQuizzesByUserIdAndQuizzerId(user_id, quizzer_id);
    }
    public List<UserQuizzes> selectUserQuizzesFromCourseId(Long course_id){
        return userQuizzersRepository.selectUserQuizzesFromCourseId(course_id);
    }
}
