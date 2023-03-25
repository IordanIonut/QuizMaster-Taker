package com.example.quizmaster.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizmaster.Adapter.QuestionAdapter;
import com.example.quizmaster.Model.Questions;
import com.example.quizmaster.Model.Quizzers;
import com.example.quizmaster.R;
import com.example.quizmaster.Service.AnswerService;
import com.example.quizmaster.Service.ApiClient;
import com.example.quizmaster.Service.QuestionService;
import com.example.quizmaster.Service.QuizzerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class QuizsActivity extends AppCompatActivity {
    private TextView quizName, quizDesctiption;
    private RecyclerView recycler_view_quiz;
    private Button startQuizNow, courseNow;
    private Intent intent;
    private Long quizzer_id;
    private Handler handler = new Handler();
    private Runnable refreshRunnable;
    private ScrollView scroll_view12;
    private LinearLayoutManager layoutManager;
    private QuestionAdapter questionAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizs);

        scroll_view12 = findViewById(R.id.scroll_view12);
        quizName = findViewById(R.id.quizName);
        quizDesctiption = findViewById(R.id.quizDesctiption);
        recycler_view_quiz = findViewById(R.id.recycler_view_quiz);
        startQuizNow = findViewById(R.id.startQuizNow);
        courseNow = findViewById(R.id.courseNow);
        recycler_view_quiz = findViewById(R.id.recycler_view_quiz);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view_quiz.setLayoutManager(layoutManager);
        questionAdapter = new QuestionAdapter(getApplicationContext(), new ArrayList<>(), new ArrayList<>());
        recycler_view_quiz.setAdapter(questionAdapter);

        long lectureId = getIntent().getLongExtra("lectureId", 0);
        QuizzerService quizzerService = ApiClient.getClient().create(QuizzerService.class);
        Call<Quizzers> callQuizzers = quizzerService.selectByLectureId(lectureId);
        callQuizzers.enqueue(new Callback<Quizzers>() {
            @Override
            public void onResponse(Call<Quizzers> call, Response<Quizzers> response) {
                if (response.isSuccessful()) {
                    Quizzers quizzers = response.body();
                    quizzer_id = quizzers.getQuizzer_id();
                    quizName.setText(quizzers.getName());
                    quizDesctiption.setText(quizzers.getDescription());
                    AnswerService answerService = ApiClient.getClient().create(AnswerService.class);
                    Call<List<Object[]>> callAnswerCount = answerService.countAnswersByQuestionIdAndAnswer(quizzers.getQuizzer_id());
                    callAnswerCount.enqueue(new Callback<List<Object[]>>() {
                        @Override
                        public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                            if (response.isSuccessful()) {
                                List<Object[]> answerList = response.body();
                                QuestionService questionService = ApiClient.getClient().create(QuestionService.class);
                                Call<List<Questions>> callQuestion = questionService.selectByQuizzerId(quizzers.getQuizzer_id());
                                callQuestion.enqueue(new Callback<List<Questions>>() {
                                    @Override
                                    public void onResponse(Call<List<Questions>> call, Response<List<Questions>> response) {
                                        if (response.isSuccessful()) {
                                            List<Questions> questions = response.body();
                                            recycler_view_quiz.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                            recycler_view_quiz.setAdapter(new QuestionAdapter(getApplicationContext(), questions, answerList));
                                        } else {
                                            Log.d("TAG", "onResponse: Error code " + response.code());
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<List<Questions>> call, Throwable t) {
                                        Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                    }
                                });
                            } else {
                                Log.d("tesa123345", "Response code: " + response.code());
                                Log.d("tesa123345", "Response body: " + response.body());
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Object[]>> call, Throwable t) {
                            Log.d("tesa123345", " " + t);
                        }
                    });
                } else {
                    Log.d("TAG", "onResponse: Error code " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Quizzers> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage(), t);
            }
        });

        handler = new Handler(Looper.getMainLooper());
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                fetchData();
                handler.postDelayed(this, 500);
            }
        };
        handler.post(refreshRunnable);
    }
    private void fetchData() {
        AnswerService answerService = ApiClient.getClient().create(AnswerService.class);
        Call<List<Object[]>> callAnswerCount = answerService.countAnswersByQuestionIdAndAnswer(quizzer_id);
        callAnswerCount.enqueue(new Callback<List<Object[]>>() {
            @Override
            public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                if (response.isSuccessful()) {
                    List<Object[]> answerList = response.body();
                    QuestionService questionService = ApiClient.getClient().create(QuestionService.class);
                    Call<List<Questions>> callQuestion = questionService.selectByQuizzerId(quizzer_id);
                    callQuestion.enqueue(new Callback<List<Questions>>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<List<Questions>> call, Response<List<Questions>> response) {
                            if (response.isSuccessful()) {
                                List<Questions> questions = response.body();
                                if (questions != null && !questions.isEmpty()) {
                                    recycler_view_quiz.setAdapter(questionAdapter);
                                    int itemCount = questions.size();
                                    int scrollRange = scroll_view12.getChildAt(0).getHeight() - scroll_view12.getHeight();
                                    int scrollY = scroll_view12.getScrollY();
                                    float scrollProgress = (float) scrollY / scrollRange;
                                    int targetPosition = (int) (scrollProgress * (itemCount - 1));
                                    questionAdapter.updateData(questions, answerList);
                                    recycler_view_quiz.scrollToPosition(targetPosition);
                                }
                            } else {
                                Log.d("TAG", "onResponse: Error code " + response.code());
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Questions>> call, Throwable t) {
                            Log.e("TAG", "onFailure: " + t.getMessage(), t);
                        }
                    });
                } else {
                    Log.d("tesa123345", "Response code: " + response.code());
                    Log.d("tesa123345", "Response body: " + response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Object[]>> call, Throwable t) {
                Log.d("tesa123345", " " + t);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(refreshRunnable);
    }
    public void startQuizNow(View view) {
        quizName.setVisibility(GONE);
        quizDesctiption.setVisibility(GONE);
        startQuizNow.setVisibility(GONE);
        recycler_view_quiz.setVisibility(VISIBLE);
        AnswerService answerService = ApiClient.getClient().create(AnswerService.class);
        Call<List<Object[]>> callAnswerCount = answerService.countAnswersByQuestionIdAndAnswer(quizzer_id);
        callAnswerCount.enqueue(new Callback<List<Object[]>>() {
            @Override
            public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                if (response.isSuccessful()) {
                    List<Object[]> answerList = response.body();
                    QuestionService questionService = ApiClient.getClient().create(QuestionService.class);
                    Call<List<Questions>> callQuestion = questionService.selectByQuizzerId(quizzer_id);
                    callQuestion.enqueue(new Callback<List<Questions>>() {
                        @Override
                        public void onResponse(Call<List<Questions>> call, Response<List<Questions>> response) {
                            if (response.isSuccessful()) {
                                List<Questions> questions = response.body();
                                recycler_view_quiz.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                recycler_view_quiz.setAdapter(new QuestionAdapter(getApplicationContext(), questions, answerList));
                            } else {
                                Log.d("TAG", "onResponse: Error code " + response.code());
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Questions>> call, Throwable t) {
                            Log.e("TAG", "onFailure: " + t.getMessage(), t);
                        }
                    });
                } else {
                    Log.d("tesa123345", "Response code: " + response.code());
                    Log.d("tesa123345", "Response body: " + response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Object[]>> call, Throwable t) {
                Log.d("tesa123345", " " + t);
            }
        });
    }
    public void courseNow(View view){
        intent = new Intent(QuizsActivity.this, LecturesActivity.class);
        long lectureId = getIntent().getLongExtra("lectureId", 0);
        long courseId = getIntent().getLongExtra("courseId", 0);
        long user_id = getIntent().getLongExtra("user_id", 0);
        intent.putExtra("user_id", user_id);
        intent.putExtra("lecture_id", lectureId);
        intent.putExtra("courseId", courseId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}