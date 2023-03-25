package com.example.quiztaker.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quiztaker.Model.*;
import com.example.quiztaker.R;
import com.example.quiztaker.Service.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class QuizsActivity extends AppCompatActivity {
    private TextView quizName, quizDesctiption, finallScore, numberQuestion, questionQuestion, timerQuestion;
    private Button startQuizNow, courseNow, btnR1, btnR2, btnR3, btnR4;
    private RelativeLayout quizzStart;
    private LinearLayout quizzPreview, quizzNext;
    private Intent intent;
    private Long quizzer_id;
    private Quizzers quizzers;
    private Users users;
    private List<Questions> questionsList;
    private List<Answers> answersList = new ArrayList<>();
    private int rasp;
    private int scor = 0;
    private int nrIntrebari;
    static int index=0;
    public int ii = 0;
    private Boolean answer = false, now = false;
    private CountDownTimer timer;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizs);

        courseNow = findViewById(R.id.courseNow);
        quizName = findViewById(R.id.quizName);
        quizDesctiption = findViewById(R.id.quizDesctiption);
        startQuizNow = findViewById(R.id.startQuizNow);
        finallScore = findViewById(R.id.finallScore);
        numberQuestion = findViewById(R.id.numberQuestion);
        questionQuestion = findViewById(R.id.questionQuestion);
        timerQuestion = findViewById(R.id.timerQuestion);
        btnR1 = findViewById(R.id.btnR1);
        btnR2 = findViewById(R.id.btnR2);
        btnR3 = findViewById(R.id.btnR3);
        btnR4 = findViewById(R.id.btnR4);
        quizzStart = findViewById(R.id.quizzStart);
        quizzPreview = findViewById(R.id.quizzPreview);
        quizzNext = findViewById(R.id.quizzNext);

        long lectureId = getIntent().getLongExtra("lectureId", 0);
        long userId = getIntent().getLongExtra("userId",0);
        long courseId = getIntent().getLongExtra("courseId",0);

        QuizzerService quizzerService = ApiClient.getClient().create(QuizzerService.class);
        UserService usersService = ApiClient.getClient().create(UserService.class);
        Call<Quizzers> callQuizzers = quizzerService.selectByLectureId(lectureId);
        callQuizzers.enqueue(new Callback<Quizzers>() {
            @Override
            public void onResponse(Call<Quizzers> call, Response<Quizzers> response) {
                if (response.isSuccessful()) {
                    quizzers = response.body();
                    quizzer_id = quizzers.getQuizzer_id();
                    quizName.setText(quizzers.getName());
                    quizDesctiption.setText(quizzers.getDescription());
                } else {
                    Log.d("TAG", "onResponse: Error code " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Quizzers> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage(), t);
            }
        });
        Call<Users> callUser = usersService.selectUserByUserId(userId);
        callUser.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()) {
                    users = response.body();
                } else {
                    Log.d("TAG", "onResponse: Error code " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage(), t);
            }
        });
        index = 0;
    }
    public void btnR1(View view){
        if(1 == rasp){
            scor++;
            numberQuestion.setText(scor + " / " + questionsList.size() );
            btnR1.setTextColor(Color.GREEN);
            answer = true;
        }
        else {
            btnR1.setTextColor(Color.RED);
            answer = false;
        }
        Answers answers = new Answers(users,quizzers, questionsList.get(index), answer);
        answersList.add(answers);
        ii++;
        timer.cancel();
        index++;
        allBotton();
        pauseDisplay(2000);
    }
    public void btnR2(View view){
        if(2 == rasp){
            scor++;
            numberQuestion.setText(scor + " / " + questionsList.size() );
            btnR2.setTextColor(Color.GREEN);
            answer = true;
        }
        else {
            btnR2.setTextColor(Color.RED);
            answer = false;
        }
        Answers answers = new Answers(users,quizzers, questionsList.get(index), answer);
        answersList.add(answers);
        ii++;
        timer.cancel();
        index++;
        allBotton();
        pauseDisplay(2000);
    }
    public void btnR3(View view){
        if(3 == rasp){
            scor++;
            numberQuestion.setText(scor + " / " + questionsList.size() );
            btnR3.setTextColor(Color.GREEN);
            answer = true;
        }
        else {
            btnR3.setTextColor(Color.RED);
            answer = false;
        }
        Answers answers = new Answers(users,quizzers, questionsList.get(index), answer);
        answersList.add(answers);
        ii++;
        index++;
        timer.cancel();
        allBotton();
        pauseDisplay(2000);

    }
    public void btnR4(View view){
        if(4 == rasp){
            scor++;
            numberQuestion.setText(scor + " / " + questionsList.size());
            btnR4.setTextColor(Color.GREEN);
            answer = true;
        }
        else {
            btnR4.setTextColor(Color.RED);
            answer = false;
        }
        Answers answers = new Answers(users,quizzers, questionsList.get(index), answer);
        answersList.add(answers);
        ii++;
        timer.cancel();
        index++;
        allBotton();
        pauseDisplay(2000);
    }
    private void allBotton(){
        btnR1.setEnabled(false);
        btnR2.setEnabled(false);
        btnR3.setEnabled(false);
        btnR4.setEnabled(false);
    }
    private void pauseDisplay(int a){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                allBotton();
                if(index == nrIntrebari){
                }
                actIntrebare(index);
            }
        }, a);
    }
    private void handleTimerCompletion() {
        btnR1.setTextColor(Color.RED);
        btnR2.setTextColor(Color.RED);
        btnR3.setTextColor(Color.RED);
        btnR4.setTextColor(Color.RED);
        answer = false;

        Answers answers = new Answers(users, quizzers, questionsList.get(index), answer);
        answersList.add(answers);
        index++;
        timer.cancel();
        allBotton();
        pauseDisplay(2000);

    }
    private void actIntrebare(int i){
        if (index == nrIntrebari) {
            quizzStart.setVisibility(GONE);
            quizzNext.setVisibility(VISIBLE);
            startQuizNow.setVisibility(VISIBLE);
            finallScore.setText(scor+ " POINT");
            now = true;
            return;
        }
        timer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerQuestion.setText(millisUntilFinished/1000+"");
            }
            public void onFinish() {
                handleTimerCompletion();
            }
        }.start();
        questionQuestion.setText(questionsList.get(index).getQuestion());

        List<String> answers = new ArrayList<>();
        answers.add(questionsList.get(index).getAnswer_1());
        answers.add(questionsList.get(index).getAnswer_2());
        answers.add(questionsList.get(index).getAnswer_3());
        answers.add(questionsList.get(index).getAnswer_4());
        Collections.shuffle(answers);
        Collections.shuffle(answers);
        btnR1.setText(answers.get(0));
        btnR2.setText(answers.get(1));
        btnR3.setText(answers.get(2));
        btnR4.setText(answers.get(3));

        if (answers.get(0).equals(questionsList.get(index).getCorrect_answer()))
            rasp = 1;
        if (answers.get(1).equals(questionsList.get(index).getCorrect_answer()))
            rasp = 2;
        if (answers.get(2).equals(questionsList.get(index).getCorrect_answer()))
            rasp = 3;
        if (answers.get(3).equals(questionsList.get(index).getCorrect_answer()))
            rasp = 4;

        btnR1.setTextColor(Color.parseColor("#FF6200EE"));
        btnR2.setTextColor(Color.parseColor("#FF6200EE"));
        btnR3.setTextColor(Color.parseColor("#FF6200EE"));
        btnR4.setTextColor(Color.parseColor("#FF6200EE"));
        btnR1.setEnabled(true);
        btnR2.setEnabled(true);
        btnR3.setEnabled(true);
        btnR4.setEnabled(true);
    }
    public void displayQuestions(){
        if(questionsList != null) {
            for (Questions questions : questionsList) {
                Log.d("test1", questions.getQuestion_id() + " " + questions.getQuestion());
            }
            numberQuestion.setText(scor + " / " + questionsList.size());
        }
    }
    public void startQuizNow(View view) {
        if(now == false) {
            quizzPreview.setVisibility(GONE);
            startQuizNow.setVisibility(GONE);
            courseNow.setVisibility(GONE);
            QuestionService questionService = ApiClient.getClient().create(QuestionService.class);
            Call<List<Questions>> callQuestion = questionService.selectByQuizzerId(quizzer_id);
            callQuestion.enqueue(new Callback<List<Questions>>() {
                @Override
                public void onResponse(Call<List<Questions>> call, Response<List<Questions>> response) {
                    if (response.isSuccessful()) {
                        questionsList = response.body();
                        displayQuestions();
                        nrIntrebari = questionsList.size();
                        quizzStart.setVisibility(VISIBLE);
                        questionQuestion.setText(questionsList.get(0).getQuestion());

                        List<String> answers = new ArrayList<>();
                        answers.add(questionsList.get(0).getAnswer_1());
                        answers.add(questionsList.get(0).getAnswer_2());
                        answers.add(questionsList.get(0).getAnswer_3());
                        answers.add(questionsList.get(0).getAnswer_4());
                        Collections.shuffle(answers);
                        Collections.shuffle(answers);
                        btnR1.setText(answers.get(0));
                        btnR2.setText(answers.get(1));
                        btnR3.setText(answers.get(2));
                        btnR4.setText(answers.get(3));
                        timer = new CountDownTimer(10000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                timerQuestion.setText(millisUntilFinished/1000+"");
                            }
                            public void onFinish() {
                                handleTimerCompletion();
                            }
                        }.start();
                        if (answers.get(0).equals(questionsList.get(0).getCorrect_answer()))
                            rasp = 1;
                        if (answers.get(1).equals(questionsList.get(0).getCorrect_answer()))
                            rasp = 2;
                        if (answers.get(2).equals(questionsList.get(0).getCorrect_answer()))
                            rasp = 3;
                        if (answers.get(3).equals(questionsList.get(0).getCorrect_answer()))
                            rasp = 4;

                        btnR1.setEnabled(true);
                        btnR2.setEnabled(true);
                        btnR3.setEnabled(true);
                        btnR4.setEnabled(true);
                    } else {
                        Log.d("TAG", "onResponse: Error code " + response.code());
                    }
                }
                @Override
                public void onFailure(Call<List<Questions>> call, Throwable t) {
                    Log.e("TAG", "onFailure: " + t.getMessage(), t);
                }
            });
        }
        else{
            AnswerService answerService = ApiClient.getClient().create(AnswerService.class);
            LectureService lectureService = ApiClient.getClient().create(LectureService.class);
            UserLectureService userLectureService = ApiClient.getClient().create(UserLectureService.class);
            UserQuizzerService userQuizzerService = ApiClient.getClient().create(UserQuizzerService.class);
            UserService userService = ApiClient.getClient().create(UserService.class);
            for(Answers answers: answersList){
                Call<Answers> callAnswer = answerService.createAnswer(answers);
                callAnswer.enqueue(new Callback<Answers>() {
                    @Override
                    public void onResponse(Call<Answers> call, Response<Answers> response) {
                        if (response.isSuccessful()) {
                        } else {
                            Log.d("TAG", "onResponse: Error code " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<Answers> call, Throwable t) {
                        Log.e("TAG", "onFailure: " + t.getMessage(), t);
                    }
                });
            }
            Call<UserLectures> callUserLecture = userLectureService.selectUserLecturesByUserIdAndLectureId(getIntent().getLongExtra("userId", 0), getIntent().getLongExtra("lectureId", 0));
            callUserLecture.enqueue(new Callback<UserLectures>() {
                @Override
                public void onResponse(Call<UserLectures> call, Response<UserLectures> response) {
                    if (response.isSuccessful()) {
                        if (response.body() == null) {
                        } else {
                        }
                    }
                }
                @Override
                public void onFailure(Call<UserLectures> call, Throwable t) {
                    Call<Users> callUser = userService.selectUserByUserId(getIntent().getLongExtra("userId", 0));
                    callUser.enqueue(new Callback<Users>() {
                        @Override
                        public void onResponse(Call<Users> call, Response<Users> response) {
                            if (response.isSuccessful()) {
                                Users user = response.body();
                                Call<Lectures> callLectures = lectureService.selectLectureByLectureId(getIntent().getLongExtra("lectureId", 0));
                                callLectures.enqueue(new Callback<Lectures>() {
                                    @Override
                                    public void onResponse(Call<Lectures> call, Response<Lectures> response) {
                                        if (response.isSuccessful()) {
                                            Lectures lectures = response.body();
                                            UserLectures userLectures = new UserLectures(user, lectures);
                                            Call<UserLectures> callUserLectures = userLectureService.createUserLectures(userLectures);
                                            callUserLectures.enqueue(new Callback<UserLectures>() {
                                                @Override
                                                public void onResponse(Call<UserLectures> call, Response<UserLectures> response) {
                                                    if (response.isSuccessful()) {

                                                    } else {
                                                        Log.d("TAG", "onResponse: Error code " + response.code());
                                                    }
                                                }
                                                @Override
                                                public void onFailure(Call<UserLectures> call, Throwable t) {
                                                    Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                                }
                                            });
                                        } else {
                                            Log.d("TAG", "onResponse: Error code " + response.code());
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Lectures> call, Throwable t) {
                                        Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                    }
                                });
                            } else {
                                Log.d("TAG", "onResponse: Error code " + response.code());
                            }
                        }
                        @Override
                        public void onFailure(Call<Users> call, Throwable t) {
                            Log.e("TAG", "onFailure: " + t.getMessage(), t);
                        }
                    });
                }
            });

            Call<UserQuizzers> callUserQuizzer = userQuizzerService.selectUserQuizzesByUserIdAndQuizzerId(getIntent().getLongExtra("userId", 0), quizzers.getQuizzer_id());
            callUserQuizzer.enqueue(new Callback<UserQuizzers>() {
                @Override
                public void onResponse(Call<UserQuizzers> call, Response<UserQuizzers> response) {
                    if (response.isSuccessful()) {
                        if (response.body() == null) {
                        } else {
                            UserQuizzers userQuizzers = response.body();
                            if(userQuizzers.getScore() < scor){
                                Call<Void> callUserQuizzers = userQuizzerService.deleteUserQuzzers(userQuizzers.getUser_quizze_id());
                                callUserQuizzers.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body() == null) {
                                            } else {
                                            }
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                    }
                                });
                                Call<Users> callUser = userService.selectUserByUserId(getIntent().getLongExtra("userId", 0));
                                callUser.enqueue(new Callback<Users>() {
                                    @Override
                                    public void onResponse(Call<Users> call, Response<Users> response) {
                                        if (response.isSuccessful()) {
                                            Users user = response.body();
                                            UserQuizzers userQuizzers = new UserQuizzers(user, quizzers, scor);
                                            Call<UserQuizzers> callUserQuizzers = userQuizzerService.createUserQuizzers(userQuizzers);
                                            callUserQuizzers.enqueue(new Callback<UserQuizzers>() {
                                                @Override
                                                public void onResponse(Call<UserQuizzers> call, Response<UserQuizzers> response) {
                                                    if (response.isSuccessful()) {
                                                    } else {
                                                        Log.d("TAG", "onResponse: Error code " + response.code());
                                                    }
                                                }
                                                @Override
                                                public void onFailure(Call<UserQuizzers> call, Throwable t) {
                                                    Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                                }
                                            });
                                        } else {
                                            Log.d("TAG", "onResponse: Error code " + response.code());

                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Users> call, Throwable t) {
                                        Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                    }
                                });
                            }
                        }
                    }
                }
                @Override
                public void onFailure(Call<UserQuizzers> call, Throwable t) {
                    Call<Users> callUser = userService.selectUserByUserId(getIntent().getLongExtra("userId", 0));
                    callUser.enqueue(new Callback<Users>() {
                        @Override
                        public void onResponse(Call<Users> call, Response<Users> response) {
                            if (response.isSuccessful()) {
                                Users user = response.body();
                                UserQuizzers userQuizzers = new UserQuizzers(user, quizzers, scor);
                                Call<UserQuizzers> callUserQuizzers = userQuizzerService.createUserQuizzers(userQuizzers);
                                callUserQuizzers.enqueue(new Callback<UserQuizzers>() {
                                    @Override
                                    public void onResponse(Call<UserQuizzers> call, Response<UserQuizzers> response) {
                                        if (response.isSuccessful()) {
                                        } else {
                                            Log.d("TAG", "onResponse: Error code " + response.code());
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<UserQuizzers> call, Throwable t) {
                                        Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                    }
                                });
                            } else {
                                Log.d("TAG", "onResponse: Error code " + response.code());

                            }
                        }
                        @Override
                        public void onFailure(Call<Users> call, Throwable t) {
                            Log.e("TAG", "onFailure: " + t.getMessage(), t);
                        }
                    });
                }
            });

            intent = new Intent(QuizsActivity.this, CoursesActivity.class);
            long lecture_id = getIntent().getLongExtra("lectureId", 0);
            intent.putExtra("lecture_id", lecture_id);
            long course_id = getIntent().getLongExtra("courseId", 0);
            intent.putExtra("courseId", course_id);
            long userID = getIntent().getLongExtra("userId", 0);
            intent.putExtra("userLoin", userID);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
    public void courseNow(View view){
        intent = new Intent(QuizsActivity.this, LecturesActivity.class);
        long lectureId = getIntent().getLongExtra("lectureId", 0);
        long courseId = getIntent().getLongExtra("courseId", 0);
        intent.putExtra("lecture_id", lectureId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("courseId", courseId);
        long userId = getIntent().getLongExtra("userLoin", 0);
        if(userId == 0)
            userId = getIntent().getLongExtra("userId", 0);
        if(userId == 0)
            userId = getIntent().getLongExtra("userLoin",0);
        intent.putExtra("userId",userId);
        startActivity(intent);
        finish();
    }
}