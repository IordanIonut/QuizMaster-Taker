package com.example.quizmaster.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizmaster.Adapter.CourseAdapter;
import com.example.quizmaster.Adapter.UserCoursesAdapter;
import com.example.quizmaster.Adapter.UserLecutureProgressAdapter;
import com.example.quizmaster.Model.*;
import com.example.quizmaster.R;
import com.example.quizmaster.Service.*;
import com.example.quizmaster.Utility.DarkModePrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.*;
public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private ArrayList<EditText[]> editTextGroups = new ArrayList<>();
    private ArrayList<TextInputLayout[]> textInputLayoutGroups = new ArrayList<>();
    private ArrayList<Button> buttonGroups = new ArrayList<Button>();
    private EditText editTextDescriptionCourse, editTextNameCourse, editTextTitileLectures, editTextLectureLectures, editTextDescriptionLectures, editTextQuizzName, editTextQuizzDescription, editTextQuestion, editTextCorrectAnswer, editTextQuestion1, editTextQuestion2, editTextQuestion3, editTextQuestion4;
    private TextInputLayout textInputTitileLectures, textInputDescriptionLectures, textInputLectureLectures, textInputQuizzName, textInputQuizzDescription, textInputQuestion, textInputCorrectAnswer, textInputQuestion1, textInputQuestion2, textInputQuestion3, textInputQuestion4;
    private Button course_id, lectures_id, quizz_id, questions_id, addButton;
    private LinearLayout linearLayout, linear_layout1, linear_layout22, linear_layout11;
    private View mAppBarMain, mAppBarCourses, mAppBarAdd, mAppBarUser;
    private ScrollView scroll_view, scroll_view1234;
    private Intent intent;
    private Courses cours = new Courses();
    private Users users = new Users();
    private Runnable refreshRunnable;
    private Handler handler = new Handler();
    private LinearLayoutManager layoutManager;
    private UserCoursesAdapter userCoursesAdapter;
    private boolean isCourseAddClicked = true, islecturesAddClicked = true, isquizzAddClicked = true, run = false;
    private RecyclerView  courseLast6, courseAll, courseAllUser, recycler_view_lecture;
    private SearchView searchView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            CourseService courseService = ApiClient.getClient().create(CourseService.class);
            UserCourseService userCourseService = ApiClient.getClient().create(UserCourseService.class);
            switch (item.getItemId()) {
                case R.id.navigationMyCourses:
                    mAppBarMain.setVisibility(GONE);
                    mAppBarCourses.setVisibility(VISIBLE);
                    mAppBarAdd.setVisibility(GONE);
                    mAppBarUser.setVisibility(GONE);
                    Call<List<Courses>> callAllCourse = courseService.selectAllCoursesByUserId(users.getUser_id());
                    callAllCourse.enqueue(new Callback<List<Courses>>() {
                        @Override
                        public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                            if (response.isSuccessful()) {
                                List<Courses> coursesList = response.body();
                                String receivedString = intent.getStringExtra("STRING_KEY");
                                courseAll.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                courseAll.setAdapter(new CourseAdapter(getApplicationContext(), coursesList, receivedString));
                            } else {
                                Log.e("TAG", "onResponse: " + response.code());
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Courses>> call, Throwable t) {
                            Log.e("TAG", "onFailure: " + t.getMessage(), t);
                        }
                    });
                    break;
                case R.id.navigationHome:
                    mAppBarMain.setVisibility(VISIBLE);
                    mAppBarCourses.setVisibility(GONE);
                    mAppBarAdd.setVisibility(GONE);
                    mAppBarUser.setVisibility(GONE);
                    Call<List<Courses>> callLast6Course = courseService.selectLast6CoursesByUserId(users.getUser_id());
                    callLast6Course.enqueue(new Callback<List<Courses>>() {
                        @Override
                        public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                            if (response.isSuccessful()) {
                                List<Courses> coursesList = response.body();
                                String receivedString = intent.getStringExtra("STRING_KEY");
                                courseLast6.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                courseLast6.setAdapter(new CourseAdapter(getApplicationContext(), coursesList, receivedString));
                            } else {
                                Log.e("TAG", "onResponse: Error code " + response.code());
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Courses>> call, Throwable t) {
                            Log.e("TAG", "onFailure: " + t.getMessage(), t);
                        }
                    });
                    break;
                case  R.id.navigationAddCourse:
                    mAppBarMain.setVisibility(GONE);
                    mAppBarCourses.setVisibility(GONE);
                    mAppBarAdd.setVisibility(VISIBLE);
                    mAppBarUser.setVisibility(GONE);
                    break;
                case  R.id.navigationPerson:
                    mAppBarMain.setVisibility(GONE);
                    mAppBarCourses.setVisibility(GONE);
                    mAppBarAdd.setVisibility(GONE);
                    mAppBarUser.setVisibility(VISIBLE);
                    Call<List<UserCourses>> callUserCourses = userCourseService.selecltAllUsersCoursesAbonmentioned(users.getUser_id());
                    callUserCourses.enqueue(new Callback<List<UserCourses>>() {
                        @Override
                        public void onResponse(Call<List<UserCourses>> call, Response<List<UserCourses>> response) {
                            List<UserCourses> userCoursesList = response.body();
                            courseAllUser.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            courseAllUser.setAdapter(new UserCoursesAdapter(getApplicationContext(), userCoursesList));
                        }
                        @Override
                        public void onFailure(Call<List<UserCourses>> call, Throwable t) {

                        }
                    });
                    break;
            }
            return true;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CourseService courseService = ApiClient.getClient().create(CourseService.class);
        intent = getIntent();
        long received= getIntent().getLongExtra("user_id",0);
        if(received == 0) {
            String receivedString = intent.getStringExtra("STRING_KEY");
            UserService userService = ApiClient.getClient().create(UserService.class);
            Call<String> callToLogin = userService.userToken(receivedString);
            callToLogin.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        String string = response.body();
                        users.setUser_id(Long.valueOf(string));
                        Call<List<Courses>> callLast6Course = courseService.selectLast6CoursesByUserId(Long.valueOf(string));
                        callLast6Course.enqueue(new Callback<List<Courses>>() {
                            @Override
                            public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                                if (response.isSuccessful()) {
                                    List<Courses> coursesList = response.body();
                                    String receivedString = intent.getStringExtra("STRING_KEY");
                                    courseLast6.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    courseLast6.setAdapter(new CourseAdapter(getApplicationContext(), coursesList, receivedString));
                                } else {
                                    Log.d("TAG", "onResponse: Error code " + response.code());
                                }
                            }
                            @Override
                            public void onFailure(Call<List<Courses>> call, Throwable t) {
                                Log.e("TAG", "onFailure: " + t.getMessage(), t);
                            }
                        });
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }else{
            users.setUser_id(received);
        }

        linearLayout = findViewById(R.id.linear_layout);
        linear_layout1 = findViewById(R.id.buttons_layout);

        if(new DarkModePrefManager(this).isNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        mAppBarMain = findViewById(R.id.content_main);
        mAppBarCourses = findViewById(R.id.app_bar_courses);
        mAppBarAdd = findViewById(R.id.app_bar_add);
        mAppBarUser = findViewById(R.id.app_bar_user);

        editTextDescriptionCourse  = findViewById(R.id.editTextDescriptionCourse);
        editTextNameCourse  = findViewById(R.id.editTextNameCourse);
        editTextDescriptionLectures = findViewById(R.id.editTextDescriptionLectures);
        editTextTitileLectures = findViewById(R.id.editTextTitileLectures);
        editTextLectureLectures = findViewById(R.id.editTextLectureLectures);
        editTextQuizzName = findViewById(R.id.editTextQuizzName);
        editTextQuizzDescription = findViewById(R.id.editTextQuizzDescription);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextCorrectAnswer = findViewById(R.id.editTextCorrectAnswer);
        editTextQuestion1 = findViewById(R.id.editTextQuestion1);
        editTextQuestion2 = findViewById(R.id.editTextQuestion2);
        editTextQuestion3 = findViewById(R.id.editTextQuestion3);
        editTextQuestion4 = findViewById(R.id.editTextQuestion4);

        textInputTitileLectures = findViewById(R.id.textInputTitileLectures);
        textInputDescriptionLectures = findViewById(R.id.textInputDescriptionLectures);
        textInputLectureLectures = findViewById(R.id.textInputLectureLectures);
        textInputQuizzName = findViewById(R.id.textInputQuizzName);
        textInputQuizzDescription = findViewById(R.id.textInputQuizzDescription);
        textInputQuestion = findViewById(R.id.textInputQuestion);
        textInputCorrectAnswer = findViewById(R.id.textInputCorrectAnswer);
        textInputQuestion1 = findViewById(R.id.textInputQuestion1);
        textInputQuestion2 = findViewById(R.id.textInputQuestion2);
        textInputQuestion3 = findViewById(R.id.textInputQuestion3);
        textInputQuestion4 = findViewById(R.id.textInputQuestion4);

        course_id =  findViewById(R.id.course_id);
        lectures_id =findViewById(R.id.lectures_id);
        quizz_id = findViewById(R.id.quizz_id);
        questions_id = findViewById(R.id.questions_id);
        addButton = findViewById(R.id.add_button);

        scroll_view = findViewById(R.id.scroll_view);
        scroll_view1234 = findViewById(R.id.scroll_view1234);
        linear_layout11 = findViewById(R.id.linear_layout11);
        linear_layout22 = findViewById(R.id.linear_layout22);
        recycler_view_lecture = findViewById(R.id.recycler_view_lecture);

        courseLast6 = findViewById(R.id.recycler_view_now);
        courseAll = findViewById(R.id.recycler_view_all);
        courseAllUser = findViewById(R.id.recycler_view_userCourses);
        recycler_view_lecture = findViewById(R.id.recycler_view_lecture);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        courseAllUser.setLayoutManager(layoutManager);
        userCoursesAdapter = new UserCoursesAdapter(getApplicationContext(), new ArrayList<>());
        courseAllUser.setAdapter(userCoursesAdapter);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return true;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        if(getIntent().getLongExtra("teacher_id12",0) != 0 && getIntent().getLongExtra("user_id12",0) != 0 && getIntent().getLongExtra("courseId12",0) != 0){
            linear_layout11.setVisibility(GONE);
            linear_layout22.setVisibility(VISIBLE);
            run = true;
            bottomNavigationView.setSelectedItemId(R.id.navigationPerson);
            UserQuizzerService userQuizzerService = ApiClient.getClient().create(UserQuizzerService.class);
            AnswerService answerService = ApiClient.getClient().create(AnswerService.class);
            Call<List<UserQuizzers>> callAllLectures = userQuizzerService.selectUserQuizzesFromCourseId(getIntent().getLongExtra("courseId12",0));
            callAllLectures.enqueue(new Callback<List<UserQuizzers>>() {
                @Override
                public void onResponse(Call<List<UserQuizzers>> call, Response<List<UserQuizzers>> response) {
                    if (response.isSuccessful()) {
                        List<UserQuizzers> userQuizzersList = response.body();
                        Call<List<Object[]>> callAnswer = answerService.countLenghtQuizzer(getIntent().getLongExtra("courseId12",0));
                        callAnswer.enqueue(new Callback<List<Object[]>>() {
                            @Override
                            public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                                if (response.isSuccessful()) {
                                    List<Object[]> answerlist = response.body();
                                    recycler_view_lecture.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    recycler_view_lecture.setAdapter(new UserLecutureProgressAdapter(getApplicationContext(), userQuizzersList, answerlist));
                                }
                            }
                            @Override
                            public void onFailure(Call<List<Object[]>> call, Throwable t) {
                                Log.e("TAG2", "onResponse: Error code " + response.code());
                            }
                        });
                    } else {
                        Log.e("TAG1", "onResponse: Error code " + response.code());
                    }
                }
                @Override
                public void onFailure(Call<List<UserQuizzers>> call, Throwable t) {
                    Log.e("TAG", "onFailure: " + t.getMessage(), t);
                }
            });
        }
        else{
            linear_layout11.setVisibility(VISIBLE);
            linear_layout22.setVisibility(GONE);
            bottomNavigationView.setSelectedItemId(R.id.navigationHome);
            run = true;
        }

        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.nav_dark_mode);

        DarkModePrefManager darkModePrefManager = new DarkModePrefManager(this);
        if (darkModePrefManager.isNightMode()) {
            menuItem.setIcon(R.drawable.outline_light_mode_black_24dp);
        }
        else {
            menuItem.setIcon(R.drawable.outline_dark_mode_black_24dp);
        }
        handler = new Handler(Looper.getMainLooper());
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                if(!run) {
                    fetchData();
                }
                handler.postDelayed(this, 500);
            }
        };
        handler.post(refreshRunnable);
    }
    private void filterData(String query) {
        CourseService courseService = ApiClient.getClient().create(CourseService.class);
        Call<List<Courses>> callCourses = courseService.selectCoursesByName(query, users.getUser_id());
        callCourses.enqueue(new Callback<List<Courses>>() {
            @Override
            public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                if (response.isSuccessful()) {
                    List<Courses> courses = response.body();
                    String receivedString = intent.getStringExtra("STRING_KEY");
                    courseAll.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    courseAll.setAdapter(new CourseAdapter(getApplicationContext(), courses, receivedString));
                }
            }
            @Override
            public void onFailure(Call<List<Courses>> call, Throwable t) {
                // Handle failure here
            }
        });
    }
    private void fetchData() {
        UserCourseService userCourseService = ApiClient.getClient().create(UserCourseService.class);
        Call<List<UserCourses>> callUserCourses = userCourseService.selecltAllUsersCoursesAbonmentioned(users.getUser_id());
        callUserCourses.enqueue(new Callback<List<UserCourses>>() {
            @Override
            public void onResponse(Call<List<UserCourses>> call, Response<List<UserCourses>> response) {
                List<UserCourses> userCoursesList = response.body();
                courseAllUser.setAdapter(userCoursesAdapter);
                int itemCount = userCoursesList.size();
                int scrollRange = scroll_view1234.getChildAt(0).getHeight() - scroll_view1234.getHeight();
                int scrollY = scroll_view1234.getScrollY();
                float scrollProgress = (float) scrollY / scrollRange;
                int targetPosition = (int) (scrollProgress * (itemCount - 1));
                userCoursesAdapter.updateData(userCoursesList);
                courseAllUser.scrollToPosition(targetPosition);
            }
            @Override
            public void onFailure(Call<List<UserCourses>> call, Throwable t) {

            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(refreshRunnable);
    }
    public void courseAdd(View view){
        String descriptionCourse = editTextDescriptionCourse.getText().toString().trim();
        String nameCourse = editTextNameCourse.getText().toString().trim();

        if (TextUtils.isEmpty(nameCourse)) {
            editTextNameCourse.setError("The name course is mandatory!");
            return;
        }
        if (TextUtils.isEmpty(descriptionCourse)) {
            editTextDescriptionCourse.setError("The description course is mandatory!");
            return;
        }
        isCourseAddClicked = !isCourseAddClicked;

        CourseService courseService = ApiClient.getClient().create(CourseService.class);
        Call<List<Courses>> calllLastCourse = courseService.selectByNameAndDescription(descriptionCourse, nameCourse, users.getUser_id());
        calllLastCourse.enqueue(new Callback<List<Courses>>() {
            @Override
            public void onResponse(Call<List<Courses>> calll, retrofit2.Response<List<Courses>> response) {
                List<Courses> courses = response.body();
                if (courses == null || courses.isEmpty()) {
                    cours = new Courses();
                } else {
                    cours = courses.get(0);
                }
            }
            @Override
            public void onFailure(Call<List<Courses>> calll, Throwable t) {
                Log.d("tag1", "Failed to retrieve courses", t);
            }
        });

        if(isCourseAddClicked){
            course_id.setRotation(0);

            editTextDescriptionCourse.setFocusableInTouchMode(true);
            editTextNameCourse.setFocusableInTouchMode(true);


            textInputDescriptionLectures.setVisibility(GONE);
            textInputTitileLectures.setVisibility(GONE);
            textInputLectureLectures.setVisibility(GONE);
            lectures_id.setVisibility(GONE);
            textInputQuizzDescription.setVisibility(GONE);
            textInputQuizzName.setVisibility(GONE);
            quizz_id.setVisibility(GONE);
            linear_layout1.setVisibility(GONE);
            textInputQuestion.setVisibility(GONE);
            textInputCorrectAnswer.setVisibility(GONE);
            textInputQuestion1.setVisibility(GONE);
            textInputQuestion2.setVisibility(GONE);
            textInputQuestion3.setVisibility(GONE);
            textInputQuestion4.setVisibility(GONE);
            questions_id.setVisibility(GONE);
            addButton.setVisibility(GONE);

            for (EditText[] editTextGroup : editTextGroups) {
                for (EditText editText : editTextGroup) {
                    editText.setVisibility(GONE);
                }
            }
            for (TextInputLayout[] editTextGroup : textInputLayoutGroups) {
                for (TextInputLayout editText : editTextGroup) {
                    editText.setVisibility(GONE);
                }
            }
            for (Button buttons : buttonGroups) {
                buttons.setVisibility(GONE);
            }
        }
        else {
            course_id.setRotation(90);
            lecturesAdd(view);
            editTextDescriptionCourse.setFocusableInTouchMode(false);
            editTextNameCourse.setFocusableInTouchMode(false);

            textInputDescriptionLectures.setVisibility(VISIBLE);
            textInputTitileLectures.setVisibility(VISIBLE);
            textInputLectureLectures.setVisibility(VISIBLE);
            lectures_id.setVisibility(VISIBLE);
        }
        scrollToBottom();
    }
    public void lecturesAdd(View view){
        String descriptionLectures = editTextDescriptionLectures.getText().toString().trim();
        String nameLectures = editTextTitileLectures.getText().toString().trim();
        String lectureLectures = editTextLectureLectures.getText().toString().trim();

        if (TextUtils.isEmpty(nameLectures)) {
            editTextTitileLectures.setError("The title lecture is mandatory!");
            return;
        }
        if (TextUtils.isEmpty(descriptionLectures)) {
            editTextDescriptionLectures.setError("The  description lecture is mandatory!");
            return;
        }
        if(TextUtils.isEmpty(lectureLectures)){
            editTextLectureLectures.setError("The lecturer lecture is mandatory!");
            return;
        }

        islecturesAddClicked = !islecturesAddClicked;
        if(islecturesAddClicked){
            lectures_id.setRotation(0);
            editTextDescriptionLectures.setFocusableInTouchMode(true);
            editTextTitileLectures.setFocusableInTouchMode(true);
            editTextLectureLectures.setFocusableInTouchMode(true);

            textInputQuizzDescription.setVisibility(GONE);
            textInputQuizzName.setVisibility(GONE);
            quizz_id.setVisibility(GONE);
            linear_layout1.setVisibility(GONE);
            textInputQuestion.setVisibility(GONE);
            textInputCorrectAnswer.setVisibility(GONE);
            textInputQuestion1.setVisibility(GONE);
            textInputQuestion2.setVisibility(GONE);
            textInputQuestion3.setVisibility(GONE);
            textInputQuestion4.setVisibility(GONE);
            questions_id.setVisibility(GONE);
            addButton.setVisibility(GONE);

            for (EditText[] editTextGroup : editTextGroups) {
                for (EditText editText : editTextGroup) {
                    editText.setVisibility(GONE);
                }
            }
            for (TextInputLayout[] editTextGroup : textInputLayoutGroups) {
                for (TextInputLayout editText : editTextGroup) {
                    editText.setVisibility(GONE);
                }
            }
            for (Button buttons : buttonGroups) {
                buttons.setVisibility(GONE);
            }
        }
        else {
            lectures_id.setRotation(90);
            quizzAdd(view);
            editTextDescriptionLectures.setFocusableInTouchMode(false);
            editTextTitileLectures.setFocusableInTouchMode(false);
            editTextLectureLectures.setFocusableInTouchMode(false);
            editTextDescriptionCourse.setFocusableInTouchMode(false);
            editTextNameCourse.setFocusableInTouchMode(false);

            textInputQuizzDescription.setVisibility(VISIBLE);
            textInputQuizzName.setVisibility(VISIBLE);
            quizz_id.setVisibility(VISIBLE);
            linear_layout1.setVisibility(VISIBLE);
        }
        scrollToBottom();
    }
    public void quizzAdd(View view){
        String questionText = editTextQuizzName.getText().toString().trim();
        String correctAnswer = editTextQuizzDescription.getText().toString().trim();

        if (TextUtils.isEmpty(questionText)) {
            editTextQuizzName.setError("The quiz name is mandatory!");
            return;
        }
        if (TextUtils.isEmpty(correctAnswer)) {
            editTextQuizzDescription.setError("The quiz description  is mandatory!");
            return;
        }

        isquizzAddClicked = !isquizzAddClicked;
        if(isquizzAddClicked){
            quizz_id.setRotation(0);

            editTextQuizzName.setFocusableInTouchMode(true);
            editTextQuizzDescription.setFocusableInTouchMode(true);

            textInputQuestion.setVisibility(GONE);
            textInputCorrectAnswer.setVisibility(GONE);
            textInputQuestion1.setVisibility(GONE);
            textInputQuestion2.setVisibility(GONE);
            textInputQuestion3.setVisibility(GONE);
            textInputQuestion4.setVisibility(GONE);
            questions_id.setVisibility(GONE);
            addButton.setVisibility(GONE);

            for (EditText[] editTextGroup : editTextGroups) {
                for (EditText editText : editTextGroup) {
                    editText.setVisibility(GONE);
                }
            }
            for (TextInputLayout[] editTextGroup : textInputLayoutGroups) {
                for (TextInputLayout editText : editTextGroup) {
                    editText.setVisibility(GONE);
                }
            }
            for (Button buttons : buttonGroups) {
                buttons.setVisibility(GONE);
            }
        }
        else {
            editTextDescriptionLectures.setFocusableInTouchMode(false);
            editTextTitileLectures.setFocusableInTouchMode(false);
            editTextLectureLectures.setFocusableInTouchMode(false);
            editTextDescriptionCourse.setFocusableInTouchMode(false);
            editTextNameCourse.setFocusableInTouchMode(false);
            editTextQuizzName.setFocusableInTouchMode(false);
            editTextQuizzDescription.setFocusableInTouchMode(false);
            quizz_id.setRotation(90);
            //questionAdd(view);

        textInputQuestion.setVisibility(VISIBLE);
            textInputCorrectAnswer.setVisibility(VISIBLE);
            textInputQuestion1.setVisibility(VISIBLE);
            textInputQuestion2.setVisibility(VISIBLE);
            textInputQuestion3.setVisibility(VISIBLE);
            textInputQuestion4.setVisibility(VISIBLE);
            questions_id.setVisibility(VISIBLE);
            addButton.setVisibility(VISIBLE);

            for (EditText[] editTextGroup : editTextGroups) {
                for (EditText editText : editTextGroup) {
                    editText.setVisibility(VISIBLE);
                }
            }
            for (TextInputLayout[] editTextGroup : textInputLayoutGroups) {
                for (TextInputLayout editText : editTextGroup) {
                    editText.setVisibility(VISIBLE);
                }
            }
            for (Button buttons : buttonGroups) {
                buttons.setVisibility(VISIBLE);
            }
        }
        scrollToBottom();
    }
    public void questionAdd(View view){
        String questionText = editTextQuestion.getText().toString().trim();
        String correctAnswer = editTextCorrectAnswer.getText().toString().trim();
        String question1 = editTextQuestion1.getText().toString().trim();
        String question2 = editTextQuestion2.getText().toString().trim();
        String question3 = editTextQuestion3.getText().toString().trim();
        String question4 = editTextQuestion4.getText().toString().trim();

        if (TextUtils.isEmpty(questionText)) {
            editTextQuestion.setError("The question text is mandatory!");
            return;
        }
        if (TextUtils.isEmpty(correctAnswer)) {
            editTextCorrectAnswer.setError("The coorect answer  is mandatory!");
            return;
        }
        if(correctAnswer.equals(question1) || correctAnswer.equals(question2) || correctAnswer.equals(question3) || correctAnswer.equals(question4)){
            if(question1.equals(question2) || question1.equals(question3) || question1.equals(question4) || question2.equals(question3) || question2.equals(question4) || question3.equals(question4)){
                if(question1.equals(question2) && question2.equals(question3) && question3.equals(question4)){
                    editTextQuestion1.setError("All answers cannot be the same!");
                    editTextQuestion2.setError("All answers cannot be the same!");
                    editTextQuestion3.setError("All answers cannot be the same!");
                    editTextQuestion4.setError("All answers cannot be the same!");
                    return;
                } else {
                    if(question1.equals(question2)){
                        editTextQuestion1.setError("The answer cannot be the same!");
                        editTextQuestion2.setError("The answer cannot be the same!");
                        return;
                    }
                    if(question1.equals(question3)){
                        editTextQuestion1.setError("The answer cannot be the same!");
                        editTextQuestion3.setError("The answer cannot be the same!");
                        return;
                    }
                    if(question1.equals(question4)){
                        editTextQuestion1.setError("The answer cannot be the same!");
                        editTextQuestion4.setError("The answer cannot be the same!");
                        return;
                    }
                    if(question2.equals(question3)){
                        editTextQuestion2.setError("The answer cannot be the same!");
                        editTextQuestion3.setError("The answer cannot be the same!");
                        return;
                    }
                    if(question2.equals(question4)){
                        editTextQuestion2.setError("The answer cannot be the same!");
                        editTextQuestion4.setError("The answer cannot be the same!");
                        return;
                    }
                    if(question3.equals(question4)){
                        editTextQuestion3.setError("The answer cannot be the same!");
                        editTextQuestion4.setError("The answer cannot be the same!");
                        return;
                    }
                }
            }
        }
        else {
            editTextCorrectAnswer.setError("The correct answer is wrong, does not exist in answer variants!");
            return;
        }
        if (TextUtils.isEmpty(question1)) {
            editTextQuestion1.setError("The answer 1  is mandatory!");
            return;
        }
        if (TextUtils.isEmpty(question2)) {
            editTextQuestion2.setError("The answer 2  is mandatory!");
            return;
        }
        if (TextUtils.isEmpty(question3)) {
            editTextQuestion3.setError("The answer 3  is mandatory!");
            return;
        }
        if (TextUtils.isEmpty(question4)) {
            editTextQuestion4.setError("The answer 4  is mandatory!");
            return;
        }

        ArrayList<String[]> groups = new ArrayList<>();
        for (EditText[] editTexts : editTextGroups) {
            String[] groupValues = new String[6];
            boolean hasValue = false;
            for (int i = 0; i < 6; i++) {
                String value = editTexts[i].getText().toString().trim();
                if (!value.isEmpty()) {
                    groupValues[i] = value;
                    hasValue = true;
                }
                if (TextUtils.isEmpty(editTexts[0].getText().toString().trim())) {
                    editTexts[0].setError("The question text is mandatory!");
                    return;
                }
                if (TextUtils.isEmpty(editTexts[0].getText().toString().trim())) {
                    editTexts[0].setError("The question text is mandatory!");
                    return;
                }
                if (TextUtils.isEmpty(editTexts[1].getText().toString().trim())) {
                    editTexts[1].setError("The coorect answer  is mandatory!");
                    return;
                }
                if(editTexts[1].getText().toString().trim().equals(editTexts[2].getText().toString().trim()) ||
                        editTexts[1].getText().toString().trim().equals(editTexts[3].getText().toString().trim()) ||
                        editTexts[1].getText().toString().trim().equals(editTexts[4].getText().toString().trim()) ||
                        editTexts[1].getText().toString().trim().equals(editTexts[5].getText().toString().trim())){
                    if(editTexts[2].getText().toString().trim().equals(editTexts[3].getText().toString().trim()) ||
                            editTexts[2].getText().toString().trim().equals(editTexts[4].getText().toString().trim()) ||
                            editTexts[2].getText().toString().trim().equals(editTexts[5].getText().toString().trim()) ||
                            editTexts[3].getText().toString().trim().equals(editTexts[4].getText().toString().trim()) ||
                            editTexts[3].getText().toString().trim().equals(editTexts[5].getText().toString().trim()) ||
                            editTexts[3].getText().toString().trim().equals(editTexts[5].getText().toString().trim())){
                        if(editTexts[2].getText().toString().trim().equals(editTexts[3].getText().toString().trim()) &&
                                editTexts[3].getText().toString().trim().equals(editTexts[4].getText().toString().trim()) &&
                                editTexts[4].getText().toString().trim().equals(editTexts[5].getText().toString().trim())){
                            editTexts[2].setError("All answers cannot be the same!");
                            editTexts[3].setError("All answers cannot be the same!");
                            editTexts[4].setError("All answers cannot be the same!");
                            editTexts[5].setError("All answers cannot be the same!");
                            return;
                        } else {
                            if(editTexts[2].getText().toString().trim().equals(editTexts[3].getText().toString().trim())){
                                editTexts[2].setError("The answer cannot be the same!");
                                editTexts[3].setError("The answer cannot be the same!");
                                return;
                            }
                            if(editTexts[2].getText().toString().trim().equals(editTexts[4].getText().toString().trim())){
                                editTexts[2].setError("The answer cannot be the same!");
                                editTexts[4].setError("The answer cannot be the same!");
                                return;
                            }
                            if(editTexts[2].getText().toString().trim().equals(editTexts[5].getText().toString().trim())){
                                editTexts[2].setError("The answer cannot be the same!");
                                editTexts[5].setError("The answer cannot be the same!");
                                return;
                            }
                            if(editTexts[3].getText().toString().trim().equals(editTexts[4].getText().toString().trim())){
                                editTexts[3].setError("The answer cannot be the same!");
                                editTexts[4].setError("The answer cannot be the same!");
                                return;
                            }
                            if(editTexts[3].getText().toString().trim().equals(editTexts[5].getText().toString().trim())){
                                editTexts[3].setError("The answer cannot be the same!");
                                editTexts[5].setError("The answer cannot be the same!");
                                return;
                            }
                            if(editTexts[4].getText().toString().trim().equals(editTexts[5].getText().toString().trim())){
                                editTexts[4].setError("The answer cannot be the same!");
                                editTexts[5].setError("The answer cannot be the same!");
                                return;
                            }
                        }
                    }
                }
                else {
                    editTexts[1].setError("The correct answer is wrong, does not exist in answer variants!");
                    return;
                }
                if (TextUtils.isEmpty(editTexts[2].getText().toString().trim())) {
                    editTexts[2].setError("The answer 1  is mandatory!");
                    return;
                }
                if (TextUtils.isEmpty(editTexts[3].getText().toString().trim())) {
                    editTexts[3].setError("The answer 2  is mandatory!");
                    return;
                }
                if (TextUtils.isEmpty(editTexts[4].getText().toString().trim())) {
                    editTexts[4].setError("The answer 3  is mandatory!");
                    return;
                }
                if (TextUtils.isEmpty(editTexts[5].getText().toString().trim())) {
                    editTexts[5].setError("The answer 4  is mandatory!");
                    return;
                }
            }
            if (hasValue) {
                groups.add(groupValues);
            }
        }
        String descriptionCourse = editTextDescriptionCourse.getText().toString().trim();
        String nameCourse = editTextNameCourse.getText().toString().trim();
        String descriptionLectures = editTextDescriptionLectures.getText().toString().trim();
        String nameLectures = editTextTitileLectures.getText().toString().trim();
        String lectureLectures = editTextLectureLectures.getText().toString().trim();
        String quizName = editTextQuizzName.getText().toString().trim();
        String quizDescription = editTextQuizzDescription.getText().toString().trim();

        lectures_id.setRotation(0);
        quizz_id.setRotation(0);
        questions_id.setRotation(0);
        course_id.setRotation(0);

        editTextDescriptionCourse.setFocusableInTouchMode(true);
        editTextNameCourse.setFocusableInTouchMode(true);
        editTextDescriptionLectures.setFocusableInTouchMode(true);
        editTextTitileLectures.setFocusableInTouchMode(true);
        editTextLectureLectures.setFocusableInTouchMode(true);
        editTextQuizzName.setFocusableInTouchMode(true);
        editTextQuizzDescription.setFocusableInTouchMode(true);

        isquizzAddClicked = true;
        islecturesAddClicked = true;
        isCourseAddClicked = true;

        textInputTitileLectures.setVisibility(GONE);
        editTextTitileLectures.setText("");
        textInputDescriptionLectures.setVisibility(GONE);
        editTextDescriptionLectures.setText("");
        textInputLectureLectures.setVisibility(GONE);
        editTextLectureLectures.setText("");
        lectures_id.setVisibility(GONE);
        textInputQuizzDescription.setVisibility(GONE);
        editTextQuizzDescription.setText("");
        textInputQuizzName.setVisibility(GONE);
        editTextQuizzName.setText("");
        quizz_id.setVisibility(GONE);
        linear_layout1.setVisibility(GONE);
        textInputQuestion.setVisibility(GONE);
        editTextQuestion.setText("");
        textInputCorrectAnswer.setVisibility(GONE);
        editTextCorrectAnswer.setText("");
        textInputQuestion1.setVisibility(GONE);
        editTextQuestion1.setText("");
        textInputQuestion2.setVisibility(GONE);
        editTextQuestion2.setText("");
        textInputQuestion3.setVisibility(GONE);
        editTextQuestion3.setText("");
        textInputQuestion4.setVisibility(GONE);
        editTextQuestion4.setText("");
        questions_id.setVisibility(GONE);
        addButton.setVisibility(GONE);

        for (EditText[] editTextGroup : editTextGroups) {
                for (EditText editText : editTextGroup) {
                    editText.setVisibility(GONE);
                    editText.setText("");
                }
            }
        for (TextInputLayout[] editTextGroup : textInputLayoutGroups) {
                for (TextInputLayout editText : editTextGroup) {
                    editText.setVisibility(GONE);
                }
            }
        for (Button buttons : buttonGroups) {
                buttons.setVisibility(GONE);
            }

        editTextDescriptionLectures.setFocusableInTouchMode(true);
        editTextTitileLectures.setFocusableInTouchMode(true);

        CourseService courseService = ApiClient.getClient().create(CourseService.class);
        LectureService lectureService = ApiClient.getClient().create(LectureService.class);
        QuizzerService quizzerService = ApiClient.getClient().create(QuizzerService.class);
        QuestionService questionService = ApiClient.getClient().create(QuestionService.class);

        if (cours.getCourse_id() == null && cours.getDescription() == null && cours.getName() == null) {
                Courses course = new Courses(nameCourse, descriptionCourse, users);
                Call<Courses> calllCreateCourse = courseService.createCourse(course);
                calllCreateCourse.enqueue(new Callback<Courses>() {
                    @Override
                    public void onResponse(Call<Courses> call, retrofit2.Response<Courses> response) {
                        Call<Courses> calllLastCourse = courseService.lastCourseInset();
                        calllLastCourse.enqueue(new Callback<Courses>() {
                            @Override
                            public void onResponse(Call<Courses> call, retrofit2.Response<Courses> response) {
                                Courses courses = response.body();
                                if (courses == null) {
                                } else {
                                    Lectures lectures = new Lectures(nameLectures, descriptionLectures, lectureLectures, courses);
                                    Call<Lectures> calllCreateLecture = lectureService.createLactures(lectures);
                                    calllCreateLecture.enqueue(new Callback<Lectures>() {
                                        @Override
                                        public void onResponse(Call<Lectures> call, retrofit2.Response<Lectures> response) {
                                            Call<Lectures> calllLastLecture = lectureService.lastLecturesInset();
                                            calllLastLecture.enqueue(new Callback<Lectures>() {
                                                @Override
                                                public void onResponse(Call<Lectures> call, retrofit2.Response<Lectures> response) {
                                                    Lectures lecture = response.body();
                                                    if (lecture == null) {
                                                    } else {
                                                        Quizzers quizzers = new Quizzers(quizName, quizDescription, lecture);
                                                        Call<Quizzers> calllCreateQuizzer = quizzerService.createQuizzer(quizzers);
                                                        calllCreateQuizzer.enqueue(new Callback<Quizzers>() {
                                                            @Override
                                                            public void onResponse(Call<Quizzers> calll, retrofit2.Response<Quizzers> response) {
                                                                Call<Quizzers> calllLastQuizzer = quizzerService.lastQuizzerInset();
                                                                calllLastQuizzer.enqueue(new Callback<Quizzers>() {
                                                                    @Override
                                                                    public void onResponse(Call<Quizzers> calll, retrofit2.Response<Quizzers> response) {
                                                                        Quizzers quizzer = response.body();
                                                                        if (quizzer == null) {
                                                                        } else {
                                                                            Questions questions = new Questions(quizzer, questionText, correctAnswer, question1, question2, question3, question4);
                                                                            Call<Questions> calllCreateQuestion = questionService.createQuestions(questions);
                                                                            calllCreateQuestion.enqueue(new Callback<Questions>() {
                                                                                @Override
                                                                                public void onResponse(Call<Questions> call, Response<Questions> response) {
                                                                                    Call<Questions> calllLastQuestion = questionService.lastQuestionInset();
                                                                                    calllLastQuestion.enqueue(new Callback<Questions>() {
                                                                                        @Override
                                                                                        public void onResponse(Call<Questions> calll, retrofit2.Response<Questions> response) {
                                                                                            Questions question = response.body();
                                                                                            if (question == null) {
                                                                                            } else {
                                                                                            }
                                                                                        }

                                                                                        @Override
                                                                                        public void onFailure(Call<Questions> calll, Throwable t) {
                                                                                            Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                                                                        }
                                                                                    });
                                                                                }

                                                                                @Override
                                                                                public void onFailure(Call<Questions> call, Throwable t) {
                                                                                    Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                                                                }
                                                                            });
                                                                            for (String[] group : groups) {
                                                                                Questions ques = new Questions(quizzer, group[0], group[1], group[2], group[3], group[4], group[5]);
                                                                                Call<Questions> calllcreate = questionService.createQuestions(ques);
                                                                                calllcreate.enqueue(new Callback<Questions>() {
                                                                                    @Override
                                                                                    public void onResponse(Call<Questions> call, Response<Questions> response) {
                                                                                    }

                                                                                    @Override
                                                                                    public void onFailure(Call<Questions> call, Throwable t) {
                                                                                        Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                                                                    }
                                                                                });
                                                                            }
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<Quizzers> calll, Throwable t) {
                                                                        Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                                                    }
                                                                });
                                                            }

                                                            @Override
                                                            public void onFailure(Call<Quizzers> calll, Throwable t) {
                                                                Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                                            }
                                                        });
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Lectures> call, Throwable t) {
                                                    Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(Call<Lectures> call, Throwable t) {
                                            Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<Courses> call, Throwable t) {
                                Log.e("TAG", "onFailure: " + t.getMessage(), t);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Courses> call, Throwable t) {
                        Log.e("TAG", "onFailure: " + t.getMessage(), t);
                    }
                });
            } else {
                Lectures lectures = new Lectures(nameLectures, descriptionLectures, lectureLectures, cours);
                Call<Lectures> calllCreateLecture = lectureService.createLactures(lectures);
                calllCreateLecture.enqueue(new Callback<Lectures>() {
                    @Override
                    public void onResponse(Call<Lectures> call, retrofit2.Response<Lectures> response) {
                        Call<Lectures> calllLastLecture = lectureService.lastLecturesInset();
                        calllLastLecture.enqueue(new Callback<Lectures>() {
                            @Override
                            public void onResponse(Call<Lectures> call, retrofit2.Response<Lectures> response) {
                                Lectures lecture = response.body();
                                if (lecture == null) {
                                } else {
                                    Quizzers quizzers = new Quizzers(quizName, quizDescription, lecture);
                                    Call<Quizzers> calllCreateQuizzer = quizzerService.createQuizzer(quizzers);
                                    calllCreateQuizzer.enqueue(new Callback<Quizzers>() {
                                        @Override
                                        public void onResponse(Call<Quizzers> calll, retrofit2.Response<Quizzers> response) {
                                            Call<Quizzers> calllLastQuizzer = quizzerService.lastQuizzerInset();
                                            calllLastQuizzer.enqueue(new Callback<Quizzers>() {
                                                @Override
                                                public void onResponse(Call<Quizzers> calll, retrofit2.Response<Quizzers> response) {
                                                    Quizzers quizzer = response.body();
                                                    if (quizzer == null) {
                                                    } else {
                                                        Questions questions = new Questions(quizzer, questionText, correctAnswer, question1, question2, question3, question4);
                                                        Call<Questions> calllCreateQuestion = questionService.createQuestions(questions);
                                                        calllCreateQuestion.enqueue(new Callback<Questions>() {
                                                            @Override
                                                            public void onResponse(Call<Questions> call, Response<Questions> response) {
                                                                Call<Questions> calllLastQuestion = questionService.lastQuestionInset();
                                                                calllLastQuestion.enqueue(new Callback<Questions>() {
                                                                    @Override
                                                                    public void onResponse(Call<Questions> calll, retrofit2.Response<Questions> response) {
                                                                        Questions question = response.body();
                                                                        if (question == null) {
                                                                        } else {
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<Questions> calll, Throwable t) {
                                                                        Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                                                    }
                                                                });
                                                            }

                                                            @Override
                                                            public void onFailure(Call<Questions> call, Throwable t) {
                                                                Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                                            }
                                                        });
                                                        for (String[] group : groups) {
                                                            Questions ques = new Questions(quizzer, group[0], group[1], group[2], group[3], group[4], group[5]);
                                                            Call<Questions> calllcreate = questionService.createQuestions(ques);
                                                            calllcreate.enqueue(new Callback<Questions>() {
                                                                @Override
                                                                public void onResponse(Call<Questions> call, Response<Questions> response) {
                                                                }

                                                                @Override
                                                                public void onFailure(Call<Questions> call, Throwable t) {
                                                                    Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                                                }
                                                            });
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Quizzers> calll, Throwable t) {
                                                    Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(Call<Quizzers> calll, Throwable t) {
                                            Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<Lectures> call, Throwable t) {
                                Log.e("TAG", "onFailure: " + t.getMessage(), t);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Lectures> call, Throwable t) {
                        Log.e("TAG", "onFailure: " + t.getMessage(), t);
                    }
                });
            }
        editTextGroups.clear();
        textInputLayoutGroups.clear();
        buttonGroups.clear();
    }
    public void addQuiz(View view) {
        addEditTextGroup(linearLayout);
    }
    @SuppressLint("ResourceAsColor")
    private void addEditTextGroup(LinearLayout linearLayout) {
        EditText[] editTexts = new EditText[6];
        TextInputLayout[] textInputLayouts = new TextInputLayout[6];
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.setPadding(30,0,30,0);

        for (int i = 0; i < 6; i++) {
            TextInputLayout textInputLayout = new TextInputLayout(this);
            textInputLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            textInputLayout.setPadding(0, 0, 0, 5); // set padding
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textInputLayout.getLayoutParams();
            params.setMargins(15, 5, params.rightMargin, params.bottomMargin);
            textInputLayout.setLayoutParams(params);

            textInputLayout.setCounterMaxLength(1);
            EditText editText = new EditText(this);
            editText.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            editText.setSingleLine(true);
            editText.setTextColor(getResources().getColor(R.color.purple_500));
            if(i == 0)
                textInputLayout.setHint("Question");
            if(i == 1)
                textInputLayout.setHint("Correct Answer");
            if(i == 2)
                textInputLayout.setHint("Answer 1");
            if(i == 3)
                textInputLayout.setHint("Answer 2");
            if(i == 4)
                textInputLayout.setHint("Answer 3");
            if(i == 5)
                textInputLayout.setHint("Answer 4");

            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.newsMoreTextSize));
            editText.setInputType(InputType.TYPE_CLASS_TEXT);

            editText.setMaxLines(1);
            textInputLayout.addView(editText);
            layout.addView(textInputLayout);
            editTexts[i] = editText;
            textInputLayouts[i] = textInputLayout;

            if (i > 0) {
                // adaugam un spatiu intre edittext-uri
                // ((LinearLayout.LayoutParams) textInputLayout.getLayoutParams()).setMargins(0, getResources().getDimensionPixelSize(0), 0, 0);
            }
        }

        Button removeButton = new Button(this);
        removeButton.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
        removeButton.setPadding(0, 0, 0,20);
        removeButton.setBackgroundResource(R.drawable.remove_xml);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) removeButton.getLayoutParams();
        params.setMargins(180, 20, 20, 20);
        removeButton.setLayoutParams(params);

        removeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextGroups.remove(editTexts);
                linearLayout.removeView(layout);
            }
        });

        layout.addView(removeButton);
        linearLayout.addView(layout, linearLayout.indexOfChild(findViewById(R.id.buttons_layout)));
        scrollToBottom();
        editTextGroups.add(editTexts);
        buttonGroups.add(removeButton);
        textInputLayoutGroups.add(textInputLayouts);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_dark_mode) {
            DarkModePrefManager darkModePrefManager = new DarkModePrefManager(this);
            item.setIcon(darkModePrefManager.isNightMode() ? R.drawable.outline_light_mode_black_24dp : R.drawable.outline_dark_mode_black_24dp);
            darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode());

            if (darkModePrefManager.isNightMode()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            recreate();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
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
    public void backUser(View view) {
        run = true;
        getIntent().removeExtra("teacher_id12");
        getIntent().removeExtra("user_id12");
        getIntent().removeExtra("courseId12");
        recreate();
        bottomNavigationView.setSelectedItemId(R.id.navigationPerson);
    }
}