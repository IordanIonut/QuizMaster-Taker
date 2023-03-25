package com.example.Spring.BootServer.Service;

import com.example.Spring.BootServer.Model.Lectures;
import com.example.Spring.BootServer.Model.UserLectures;
import com.example.Spring.BootServer.Repository.LecturesRepository;
import com.example.Spring.BootServer.Repository.UserLecutresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLecturesService {
    @Autowired
    private UserLecutresRepository userLecutresRepository;
    public List<UserLectures> getUserLectures() {
        return userLecutresRepository.findAll();
    }
    public UserLectures addUserLectures(UserLectures quizzers) {
        return userLecutresRepository.save(quizzers);
    }
    public UserLectures updateUserLectures(UserLectures userCourse) {
        UserLectures existingUser = userLecutresRepository.findById(userCourse.getUsers_lecture_id()).orElse(null);
        if (existingUser == null) {
            return null;
        }
        return userLecutresRepository.save(userCourse);
    }
    public void deleteUserLecutres(Long id) {
        userLecutresRepository.deleteById(id);
    }
    public UserLectures selectUserLecturesByUserIdAndLectureId(Long user_id, Long lecture_id){
        return userLecutresRepository.selectUserLecturesByUserIdAndLectureId(user_id, lecture_id);
    }
    public List<UserLectures> selectUserLecturesByUserId(Long user_id){
        return userLecutresRepository.selectUserLecturesByUserId(user_id);
    }
}
