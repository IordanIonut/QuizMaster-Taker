package com.example.quiztaker.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quiztaker.Adapter.LectureAdapter;
import com.example.quiztaker.Model.*;
import com.example.quiztaker.R;
import com.example.quiztaker.Service.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.FOCUS_DOWN;

public class CoursesActivity extends AppCompatActivity {
    private Intent intent;
    private TextView nameCourseSelected, descriptionCourseSelected;
    private RecyclerView recycler_view_lecturer;
    private ScrollView scroll_view;
    private Handler handler = new Handler();
    private Runnable refreshRunnable;
    private ScrollView scroll_view123;
    private LinearLayoutManager layoutManager;
    private LectureAdapter questionAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        UserService userService = ApiClient.getClient().create(UserService.class);
        CourseService courseService = ApiClient.getClient().create(CourseService.class);
        LectureService lectureService = ApiClient.getClient().create(LectureService.class);
        QuizzerService quizzerService = ApiClient.getClient().create(QuizzerService.class);
        QuestionService questionService = ApiClient.getClient().create(QuestionService.class);
        UserCourseService userCourseService = ApiClient.getClient().create(UserCourseService.class);
        UserLectureService userLectureService  = ApiClient.getClient().create(UserLectureService.class);

        long lecture_id = getIntent().getLongExtra("lecture_id", 0);
        long courseId = getIntent().getLongExtra("courseId", 0);
        long userId = getIntent().getLongExtra("userLoin", 0);
        if(userId == 0)
            userId = getIntent().getLongExtra("userId", 0);
        if(userId == 0)
            userId = getIntent().getLongExtra("userLoin",0);
        Call<UserCourses> callUsersCoursesUserIdCourseId = userCourseService.selectUserCourseByUserIdAndCourseId(userId, courseId);
        long finalUserId1 = userId;
        callUsersCoursesUserIdCourseId.enqueue(new Callback<UserCourses>() {
            @Override
            public void onResponse(Call<UserCourses> call, Response<UserCourses> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                    } else {
                    }
                }
            }
            @Override
            public void onFailure(Call<UserCourses> call, Throwable t) {
                Call<Users> callUsers = userService.selectUserByUserId(finalUserId1);
                callUsers.enqueue(new Callback<Users>() {
                    @Override
                    public void onResponse(Call<Users> call, Response<Users> response) {
                        if (response.isSuccessful()) {
                            Users users = response.body();
                            Call<Courses> callCourses = courseService.selectCourseByCourseId(courseId);
                            callCourses.enqueue(new Callback<Courses>() {
                                @Override
                                public void onResponse(Call<Courses> call, Response<Courses> response) {
                                    if (response.isSuccessful()) {
                                        Courses courses = response.body();
                                        UserCourses userCourses = new UserCourses(users, courses);
                                        Call<UserCourses> callAdd = userCourseService.addUserCourse(userCourses);
                                        callAdd.enqueue(new Callback<UserCourses>() {
                                            @Override
                                            public void onResponse(Call<UserCourses> call, Response<UserCourses> response) {
                                                Log.d("TAG", "onResponse: Error code " + response.code());
                                            }

                                            @Override
                                            public void onFailure(Call<UserCourses> call, Throwable t) {
                                                Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                            }
                                        });

                                    } else {
                                        Log.d("TAG", "onResponse: Error code " + response.code());
                                    }
                                }
                                @Override
                                public void onFailure(Call<Courses> call, Throwable t) {
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

        nameCourseSelected = findViewById(R.id.nameCourseSelected);
        descriptionCourseSelected = findViewById(R.id.descriptionCourseSelected);
        recycler_view_lecturer = findViewById(R.id.recycler_view_lecturer);

        scroll_view = findViewById(R.id.scroll_view);

        Call<Courses> callCourse = courseService.selectCourseByCourseId(courseId);
        callCourse.enqueue(new Callback<Courses>() {
            @Override
            public void onResponse(Call<Courses> call, Response<Courses> response) {
                if (response.isSuccessful()) {
                    Courses course = response.body();
                    nameCourseSelected.setText(course.getName());
                    descriptionCourseSelected.setText(course.getDescription());
                } else {
                    Log.d("TAG", "onResponse: Error code " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Courses> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage(), t);
            }
        });

        Call<List<Lectures>> callLecturesAll = lectureService.selectLecutreByCourseId(courseId);
        long finalUserId = userId;
        long finalUserId2 = userId;
        callLecturesAll.enqueue(new Callback<List<Lectures>>() {
            @Override
            public void onResponse(Call<List<Lectures>> call, Response<List<Lectures>> response) {
                if (response.isSuccessful()) {
                    List<Lectures> lecturesList = response.body();
                    Call<List<UserLectures>> callUserLectures = userLectureService.selectUserLecturesByUserId(finalUserId2);
                    callUserLectures.enqueue(new Callback<List<UserLectures>>() {
                        @Override
                        public void onResponse(Call<List<UserLectures>> call, Response<List<UserLectures>> response) {
                            if(response.isSuccessful()) {
                                List<UserLectures> userLectures = response.body();
                                recycler_view_lecturer.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                recycler_view_lecturer.setAdapter(new LectureAdapter(getApplicationContext(), lecturesList, finalUserId, userLectures));
                            }else{
                                Log.d("TAG", "onResponse: Error code " + response.code());
                            }
                        }
                        @Override
                        public void onFailure(Call<List<UserLectures>> call, Throwable t) {
                            Log.d("TAG", "onResponse: Error code " + response.code());
                        }
                    });
                } else {
                    Log.d("TAG", "onResponse: Error code " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<Lectures>> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage(), t);
            }
        });

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view_lecturer.setLayoutManager(layoutManager);
        questionAdapter = new LectureAdapter(getApplicationContext(), new ArrayList<>(), 1l, new ArrayList<>());
        recycler_view_lecturer.setAdapter(questionAdapter);
        scroll_view123 = findViewById(R.id.scroll_view123);

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
        UserService userService = ApiClient.getClient().create(UserService.class);
        CourseService courseService = ApiClient.getClient().create(CourseService.class);
        LectureService lectureService = ApiClient.getClient().create(LectureService.class);
        QuizzerService quizzerService = ApiClient.getClient().create(QuizzerService.class);
        QuestionService questionService = ApiClient.getClient().create(QuestionService.class);
        UserCourseService userCourseService = ApiClient.getClient().create(UserCourseService.class);
        UserLectureService userLectureService  = ApiClient.getClient().create(UserLectureService.class);

        long lecture_id = getIntent().getLongExtra("lecture_id", 0);
        long courseId = getIntent().getLongExtra("courseId", 0);
        long userId = getIntent().getLongExtra("userLoin", 0);
        if(userId == 0)
            userId = getIntent().getLongExtra("userId", 0);
        if(userId == 0)
            userId = getIntent().getLongExtra("userLoin",0);
        Call<List<Lectures>> callLecturesAll = lectureService.selectLecutreByCourseId(courseId);
        long finalUserId = userId;
        callLecturesAll.enqueue(new Callback<List<Lectures>>() {
            @Override
            public void onResponse(Call<List<Lectures>> call, Response<List<Lectures>> response) {
                if (response.isSuccessful()) {
                    List<Lectures> lecturesList = response.body();
                    Call<List<UserLectures>> callUserLectures = userLectureService.selectUserLecturesByUserId(finalUserId);
                    callUserLectures.enqueue(new Callback<List<UserLectures>>() {
                        @Override
                        public void onResponse(Call<List<UserLectures>> call, Response<List<UserLectures>> response) {
                            if(response.isSuccessful()) {
                                List<UserLectures> userLectures = response.body();
                                recycler_view_lecturer.setAdapter(questionAdapter);
                                int itemCount = userLectures.size();
                                int scrollRange = scroll_view123.getChildAt(0).getHeight() - scroll_view123.getHeight();
                                int scrollY = scroll_view123.getScrollY();
                                float scrollProgress = (float) scrollY / scrollRange;
                                int targetPosition = (int) (scrollProgress * (itemCount - 1));
                                questionAdapter.updateData(lecturesList, finalUserId, userLectures);
                                recycler_view_lecturer.scrollToPosition(targetPosition);
                            }else{
                                Log.d("TAG", "onResponse: Error code " + response.code());
                            }
                        }
                        @Override
                        public void onFailure(Call<List<UserLectures>> call, Throwable t) {
                            Log.d("TAG", "onResponse: Error code " + response.code());
                        }
                    });
                } else {
                    Log.d("TAG", "onResponse: Error code " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<Lectures>> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage(), t);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(refreshRunnable);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    private void scrollToView(final View view) {
        scroll_view.post(new Runnable() {
            @Override
            public void run() {
                scroll_view.scrollTo(0, view.getBottom());
            }
        });
    }
    private void scrollToBottom() {
        scroll_view.post(new Runnable() {
            @Override
            public void run() {
                scroll_view.fullScroll(FOCUS_DOWN);
            }
        });
    }
    public void backHome(View view) {
        intent = new Intent(CoursesActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        long userId = getIntent().getLongExtra("userLoin", 0);
        if(userId == 0)
            userId = getIntent().getLongExtra("userId", 0);
        if(userId == 0)
            userId = getIntent().getLongExtra("userLoin",0);
        intent.putExtra("user_id", userId);
        startActivity(intent);
        finish();
    }
    public void describeShow(View view) {
        if (descriptionCourseSelected.getVisibility() == View.VISIBLE) {
            descriptionCourseSelected.setVisibility(View.GONE);
        } else {
            descriptionCourseSelected.setVisibility(View.VISIBLE);
        }
    }
}