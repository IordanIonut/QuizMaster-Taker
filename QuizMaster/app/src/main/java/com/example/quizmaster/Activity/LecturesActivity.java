package com.example.quizmaster.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizmaster.Model.Lectures;
import com.example.quizmaster.R;
import com.example.quizmaster.Service.ApiClient;
import com.example.quizmaster.Service.LectureService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LecturesActivity extends AppCompatActivity {
    private Intent intent;
    private TextView lecturesTitle, lecturesLecuture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectures);

        long lecture_id = getIntent().getLongExtra("lecture_id", 0);
        lecturesTitle = findViewById(R.id.lecturesTitle);
        lecturesLecuture = findViewById(R.id.lecturesLecuture);


        LectureService lectureService = ApiClient.getClient().create(LectureService.class);
        Call<Lectures> callLecture = lectureService.selectLectureByLectureId(lecture_id);
        callLecture.enqueue(new Callback<Lectures>() {
            @Override
            public void onResponse(Call<Lectures> call, Response<Lectures> response) {
                if (response.isSuccessful()) {
                    Lectures lectures = response.body();
                    lecturesTitle.setText(lectures.getTitle());
                    lecturesLecuture.setText(lectures.getLecture());
                } else {
                    Log.d("TAG", "onResponse: Error code " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Lectures> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage(), t);
            }
        });
    }
    public void startQuiz(View view) {
        intent = new Intent(LecturesActivity.this, QuizsActivity.class);
        long lecture_id = getIntent().getLongExtra("lecture_id", 0);
        intent.putExtra("lectureId", lecture_id);
        long course_id = getIntent().getLongExtra("courseId", 0);
        intent.putExtra("courseId", course_id);
        long user_id = getIntent().getLongExtra("user_id", 0);
        intent.putExtra("user_id", user_id);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    public void backCourses(View view) {
        intent = new Intent(LecturesActivity.this, CoursesActivity.class);
        long course_id = getIntent().getLongExtra("courseId", 0);
        intent.putExtra("courseId", course_id);
        long user_id = getIntent().getLongExtra("user_id", 0);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
        finish();
    }
}