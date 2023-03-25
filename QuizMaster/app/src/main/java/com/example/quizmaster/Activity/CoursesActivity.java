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
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizmaster.Adapter.LectureAdapter;
import com.example.quizmaster.Model.Courses;
import com.example.quizmaster.Model.Lectures;
import com.example.quizmaster.Model.Questions;
import com.example.quizmaster.Model.Quizzers;
import com.example.quizmaster.R;
import com.example.quizmaster.Service.*;
import com.google.android.material.textfield.TextInputLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.*;

public class CoursesActivity extends AppCompatActivity {
    private Intent intent;
    private TextView nameCourseSelected, descriptionCourseSelected;
    private RecyclerView recycler_view_lecturer;
    private Button backHome, describeShow, addLecture;
    private ScrollView scroll_view;
    private ArrayList<EditText[]> editTextGroups = new ArrayList<>();
    private ArrayList<TextInputLayout[]> textInputLayoutGroups = new ArrayList<>();
    private ArrayList<Button> buttonGroups = new ArrayList<Button>();
    private ArrayList<LinearLayout> linearLayoutList = new ArrayList<>();
    private EditText editTextTitileLectures, editTextLectureLectures, editTextDescriptionLectures, editTextQuizzName, editTextQuizzDescription, editTextQuestion, editTextCorrectAnswer, editTextQuestion1, editTextQuestion2, editTextQuestion3, editTextQuestion4;
    private TextInputLayout textInputTitileLectures, textInputDescriptionLectures, textInputLectureLectures, textInputQuizzName, textInputQuizzDescription, textInputQuestion, textInputCorrectAnswer, textInputQuestion1, textInputQuestion2, textInputQuestion3, textInputQuestion4;
    private Button lectures_id, quizz_id, questions_id, addButton, delete_id;
    private boolean isCourseAddClicked = true, islecturesAddClicked = true, isquizzAddClicked = true;
    private LinearLayout linearLayout, linear_layout2, linear_layoutasd, buttons_layout;
    private Long quizID, questionsID;
    private boolean addbuttonID = false;
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

        CourseService courseService = ApiClient.getClient().create(CourseService.class);
        LectureService lectureService = ApiClient.getClient().create(LectureService.class);
        QuizzerService quizzerService = ApiClient.getClient().create(QuizzerService.class);
        QuestionService questionService = ApiClient.getClient().create(QuestionService.class);

        long lecture_id = getIntent().getLongExtra("lecture_id", 0);
        long courseId = getIntent().getLongExtra("courseId", 0);
        long receivedString = getIntent().getLongExtra("user_id",0);
        Log.d("asd123",receivedString + " ");

        nameCourseSelected = findViewById(R.id.nameCourseSelected);
        linear_layout2 = findViewById(R.id.linear_layout2);
        descriptionCourseSelected = findViewById(R.id.descriptionCourseSelected);
        recycler_view_lecturer = findViewById(R.id.recycler_view_lecturer);
        backHome = findViewById(R.id.backHome);
        describeShow = findViewById(R.id.describeShow);
        addLecture = findViewById(R.id.addLecture);
        linear_layoutasd = findViewById(R.id.linear_layoutasd);
        buttons_layout = findViewById(R.id.buttons_layout);
        delete_id = findViewById(R.id.delete_id);

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

        editTextDescriptionLectures.setText(" ");
        editTextTitileLectures.setText(" ");
        editTextLectureLectures.setText(" ");
        editTextQuizzName.setText(" ");
        editTextQuizzDescription.setText(" ");
        editTextQuestion.setText(" ");
        editTextCorrectAnswer.setText(" ");
        editTextQuestion1.setText(" ");
        editTextQuestion2.setText(" ");
        editTextQuestion3.setText(" ");
        editTextQuestion4.setText(" ");

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

        lectures_id =findViewById(R.id.lectures_id);
        quizz_id = findViewById(R.id.quizz_id);
        questions_id = findViewById(R.id.questions_id);
        addButton = findViewById(R.id.add_button);

        scroll_view = findViewById(R.id.scroll_view);
        refreshPage();

        if(lecture_id != 0){
            addbuttonID = false;
            nameCourseSelected.setVisibility(GONE);
            descriptionCourseSelected.setVisibility(GONE);
            recycler_view_lecturer.setVisibility(GONE);
            addLecture.setVisibility(GONE);
            describeShow.setVisibility(GONE);
            delete_id.setVisibility(VISIBLE);
            backHome.setVisibility(GONE);
            linear_layoutasd.setVisibility(VISIBLE);
            Call<Quizzers> callQuizzer = quizzerService.selectByLectureId(lecture_id);
            callQuizzer.enqueue(new Callback<Quizzers>() {
                @Override
                public void onResponse(Call<Quizzers> call, Response<Quizzers> response) {
                    Quizzers quizzers = response.body();
                    if (quizzers == null) {
                        Log.d("test1", "boss nu merge courses");
                    } else {
                        editTextQuizzName.setText(quizzers.getName());
                        editTextQuizzDescription.setText(quizzers.getDescription());
                        editTextDescriptionLectures.setText(quizzers.getLecture_id().getDescription());
                        editTextTitileLectures.setText(quizzers.getLecture_id().getTitle());
                        editTextLectureLectures.setText(quizzers.getLecture_id().getLecture());
                        quizID = quizzers.getQuizzer_id();
                        Call<List<Questions>> callQuestions = questionService.selectByQuizzerId(quizzers.getQuizzer_id());
                        callQuestions.enqueue(new Callback<List<Questions>>() {
                            @Override
                            public void onResponse(Call<List<Questions>> call, Response<List<Questions>> response) {
                                List<Questions> questionsList = response.body();
                                if (questionsList == null) {
                                    Log.d("test1", "boss nu merge courses");
                                } else {
                                    editTextQuestion.setText(questionsList.get(0).getQuestion());
                                    editTextCorrectAnswer.setText(questionsList.get(0).getCorrect_answer());
                                    editTextQuestion1.setText(questionsList.get(0).getAnswer_1());
                                    editTextQuestion2.setText(questionsList.get(0).getAnswer_2());
                                    editTextQuestion3.setText(questionsList.get(0).getAnswer_3());
                                    editTextQuestion4.setText(questionsList.get(0).getAnswer_4());
                                    questionsID = questionsList.get(0).getQuestion_id();
                                    for (int j = 1; j < questionsList.size(); j++) {
                                        EditText[] editTexts = new EditText[6];
                                        TextInputLayout[] textInputLayouts = new TextInputLayout[6];
                                        LinearLayout layout = new LinearLayout(CoursesActivity.this);
                                        layout.setId(Math.toIntExact(questionsList.get(j).getQuestion_id()));

                                        layout.setOrientation(LinearLayout.VERTICAL);
                                        layout.setLayoutParams(new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT));
                                        layout.setPadding(30, 0, 30, 0);

                                        for (int i = 0; i < 6; i++) {
                                            TextInputLayout textInputLayout = new TextInputLayout(CoursesActivity.this);
                                            textInputLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                                            textInputLayout.setPadding(0, 0, 0, 5);
                                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textInputLayout.getLayoutParams();
                                            params.setMargins(15, 5, params.rightMargin, params.bottomMargin);
                                            textInputLayout.setLayoutParams(params);

                                            textInputLayout.setCounterMaxLength(1);
                                            EditText editText = new EditText(CoursesActivity.this);
                                            editText.setLayoutParams(new LinearLayout.LayoutParams(
                                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                                            editText.setInputType(InputType.TYPE_CLASS_TEXT);
                                            editText.setSingleLine(true);
                                            editText.setTextColor(getResources().getColor(R.color.purple_500));
                                            if (i == 0) {
                                                textInputLayout.setHint("Question");
                                                editText.setText(questionsList.get(j).getQuestion());
                                            }
                                            if (i == 1) {
                                                textInputLayout.setHint("Correct Answer");
                                                editText.setText(questionsList.get(j).getCorrect_answer());
                                            }
                                            if (i == 2) {
                                                textInputLayout.setHint("Answer 1");
                                                editText.setText(questionsList.get(j).getAnswer_1());
                                            }
                                            if (i == 3) {
                                                textInputLayout.setHint("Answer 2");
                                                editText.setText(questionsList.get(j).getAnswer_2());
                                            }
                                            if (i == 4) {
                                                textInputLayout.setHint("Answer 3");
                                                editText.setText(questionsList.get(j).getAnswer_3());
                                            }
                                            if (i == 5) {
                                                textInputLayout.setHint("Answer 4");
                                                editText.setText(questionsList.get(j).getAnswer_4());
                                            }
                                            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.newsMoreTextSize));
                                            editText.setInputType(InputType.TYPE_CLASS_TEXT);

                                            editText.setMaxLines(1);
                                            textInputLayout.addView(editText);
                                            layout.addView(textInputLayout);
                                            editTexts[i] = editText;
                                            textInputLayouts[i] = textInputLayout;

                                        }

                                        Button removeButton = new Button(CoursesActivity.this);
                                        removeButton.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
                                        removeButton.setPadding(0, 0, 0, 20);
                                        removeButton.setBackgroundResource(R.drawable.remove_xml);
                                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) removeButton.getLayoutParams();
                                        params.setMargins(180, 20, 20, 20);
                                        removeButton.setLayoutParams(params);

                                        int finalJ = j;
                                        removeButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                int index = linear_layoutasd.indexOfChild(layout) / questionsList.size() - 1;
                                                linear_layoutasd.removeView(layout);
                                                editTextGroups.remove(index - 1);
                                                buttonGroups.remove(removeButton);
                                                Call<Void> callDelete = questionService.deleteQuestions(questionsList.get(finalJ).getQuestion_id());
                                                callDelete.enqueue(new Callback<Void>() {
                                                    @Override
                                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Void> call, Throwable t) {
                                                        Log.e("TAG1", "onFailure: " + t.getMessage(), t);
                                                    }
                                                });
                                            }
                                        });
                                        layout.addView(removeButton);
                                        linearLayoutList.add(layout);
                                        linear_layoutasd.addView(layout, linear_layoutasd.indexOfChild(findViewById(R.id.buttons_layout)));
                                        editTextGroups.add(editTexts);
                                        buttonGroups.add(removeButton);
                                        textInputLayoutGroups.add(textInputLayouts);

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
                                }
                            }
                            @Override
                            public void onFailure(Call<List<Questions>> call, Throwable t) {
                                Log.e("TAG1", "onFailure: " + t.getMessage(), t);
                            }
                        });
                    }
                }
                @Override
                public void onFailure(Call<Quizzers> call, Throwable t) {
                    Log.e("TAG1", "onFailure: " + t.getMessage(), t);
                }
            });
        }
        else{
            linear_layout2.setVisibility(VISIBLE);
        }

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view_lecturer.setLayoutManager(layoutManager);
        questionAdapter = new LectureAdapter(getApplicationContext(), new ArrayList<>());
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
        long courseId = getIntent().getLongExtra("courseId", 0);
        LectureService lectureService = ApiClient.getClient().create(LectureService.class);
        Call<List<Lectures>> callLecturesAll = lectureService.selectLecutreByCourseId(courseId);
        callLecturesAll.enqueue(new Callback<List<Lectures>>() {
            @Override
            public void onResponse(Call<List<Lectures>> call, Response<List<Lectures>> response) {
                if (response.isSuccessful()) {
                    List<Lectures> lecturesList = response.body();
                    recycler_view_lecturer.setAdapter(questionAdapter);
                    int itemCount = lecturesList.size();
                    int scrollRange = scroll_view123.getChildAt(0).getHeight() - scroll_view123.getHeight();
                    int scrollY = scroll_view123.getScrollY();
                    float scrollProgress = (float) scrollY / scrollRange;
                    int targetPosition = (int) (scrollProgress * (itemCount - 1));
                    questionAdapter.updateData(lecturesList);
                    recycler_view_lecturer.scrollToPosition(targetPosition);
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
    private void refreshPage() {
        CourseService courseService = ApiClient.getClient().create(CourseService.class);
        LectureService lectureService = ApiClient.getClient().create(LectureService.class);
        QuizzerService quizzerService = ApiClient.getClient().create(QuizzerService.class);
        QuestionService questionService = ApiClient.getClient().create(QuestionService.class);
        long lecture_id = getIntent().getLongExtra("lecture_id", 0);
        long courseId = getIntent().getLongExtra("courseId", 0);
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
        callLecturesAll.enqueue(new Callback<List<Lectures>>() {
            @Override
            public void onResponse(Call<List<Lectures>> call, Response<List<Lectures>> response) {
                if (response.isSuccessful()) {
                    List<Lectures> lecturesList = response.body();
                    recycler_view_lecturer.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recycler_view_lecturer.setAdapter(new LectureAdapter(getApplicationContext(), lecturesList));
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
            textInputQuestion.setVisibility(GONE);
            textInputCorrectAnswer.setVisibility(GONE);
            textInputQuestion1.setVisibility(GONE);
            textInputQuestion2.setVisibility(GONE);
            textInputQuestion3.setVisibility(GONE);
            textInputQuestion4.setVisibility(GONE);
            questions_id.setVisibility(GONE);
            addButton.setVisibility(GONE);
            quizzAdd(view);
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

            editTextDescriptionLectures.setFocusableInTouchMode(false);
            editTextTitileLectures.setFocusableInTouchMode(false);
            editTextLectureLectures.setFocusableInTouchMode(false);

            textInputQuizzDescription.setVisibility(VISIBLE);
            textInputQuizzName.setVisibility(VISIBLE);
            quizz_id.setVisibility(VISIBLE);
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
            editTextQuizzName.setFocusableInTouchMode(false);
            editTextQuizzDescription.setFocusableInTouchMode(false);
            quizz_id.setRotation(90);

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

        int j = 0;
        ArrayList<String[]> groups = new ArrayList<>();
        for (EditText[] editTexts : editTextGroups) {
            String[] groupValues = new String[7];
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
            groupValues[6] = String.valueOf(linearLayoutList.get(j).getId());
            if (hasValue) {
                groups.add(groupValues);
            }
            j++;
        }


        String descriptionLectures = editTextDescriptionLectures.getText().toString().trim();
        String nameLectures = editTextTitileLectures.getText().toString().trim();
        String lectureLectures = editTextLectureLectures.getText().toString().trim();
        String quizName = editTextQuizzName.getText().toString().trim();
        String quizDescription = editTextQuizzDescription.getText().toString().trim();

        lectures_id.setRotation(0);
        quizz_id.setRotation(0);
        questions_id.setRotation(0);

        editTextDescriptionLectures.setFocusableInTouchMode(true);
        editTextTitileLectures.setFocusableInTouchMode(true);
        editTextLectureLectures.setFocusableInTouchMode(true);
        editTextQuizzName.setFocusableInTouchMode(true);
        editTextQuizzDescription.setFocusableInTouchMode(true);

        isquizzAddClicked = true;
        islecturesAddClicked = true;
        isCourseAddClicked = true;

        textInputQuizzDescription.setVisibility(GONE);
        textInputQuizzName.setVisibility(GONE);
        quizz_id.setVisibility(GONE);
        linear_layout2.setVisibility(GONE);
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

        editTextDescriptionLectures.setFocusableInTouchMode(true);
        editTextTitileLectures.setFocusableInTouchMode(true);

        CourseService courseService = ApiClient.getClient().create(CourseService.class);
        LectureService lectureService = ApiClient.getClient().create(LectureService.class);
        QuizzerService quizzerService = ApiClient.getClient().create(QuizzerService.class);
        QuestionService questionService = ApiClient.getClient().create(QuestionService.class);

        if(addbuttonID == false) {
            Call<Courses> callCourse = courseService.selectCourseByCourseId(getIntent().getLongExtra("courseId", 0));
            callCourse.enqueue(new Callback<Courses>() {
                @Override
                public void onResponse(Call<Courses> call, retrofit2.Response<Courses> response) {
                    Courses courses = response.body();
                    Lectures lectures = new Lectures(getIntent().getLongExtra("lecture_id", '0'), nameLectures, descriptionLectures, lectureLectures, courses);
                    Call<Lectures> calllCreateLecture = lectureService.updateLectures(lectures);
                    calllCreateLecture.enqueue(new Callback<Lectures>() {
                        @Override
                        public void onResponse(Call<Lectures> call, retrofit2.Response<Lectures> response) {
                            Lectures lecture = response.body();
                            if (lecture == null) {
                            } else {
                                Quizzers quizzers = new Quizzers(quizID, quizName, quizDescription, lecture);
                                Call<Quizzers> calllCreateQuizzer = quizzerService.updateQuizzers(quizzers);
                                calllCreateQuizzer.enqueue(new Callback<Quizzers>() {
                                    @Override
                                    public void onResponse(Call<Quizzers> calll, retrofit2.Response<Quizzers> response) {
                                        Quizzers quizzer = response.body();
                                        if (quizzer == null) {
                                        } else {
                                            Questions questions = new Questions(questionsID, quizzer, questionText, question1, question2, question3, question4, correctAnswer);
                                            Call<Questions> calllCreateQuestion = questionService.updateQuestions(questions);
                                            calllCreateQuestion.enqueue(new Callback<Questions>() {
                                                @Override
                                                public void onResponse(Call<Questions> call, Response<Questions> response) {
                                                    Questions question = response.body();
                                                    if (question == null) {
                                                    } else {
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Questions> call, Throwable t) {
                                                    Log.e("TAG", "onFailure: " + t.getMessage(), t);
                                                }
                                            });
                                            for (String[] group : groups) {
                                                if (Long.parseLong(group[6]) != -1) {
                                                    Questions ques = new Questions(Long.parseLong(group[6]), quizzer, group[0], group[2], group[3], group[4], group[5], group[1]);
                                                    Call<Questions> calllcreate = questionService.updateQuestions(ques);
                                                    calllcreate.enqueue(new Callback<Questions>() {
                                                        @Override
                                                        public void onResponse(Call<Questions> call, Response<Questions> response) {
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Questions> call, Throwable t) {
                                                            Log.e("TAG", "onFailure: " + t.getMessage(), t);                                                        }
                                                    });
                                                } else {
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
                public void onFailure(Call<Courses> call, Throwable t) {
                    Log.e("TAG", "onFailure: " + t.getMessage(), t);
                }
            });
        }else{
            Call<Courses> callCourse = courseService.selectCourseByCourseId(getIntent().getLongExtra("courseId", 0));
            callCourse.enqueue(new Callback<Courses>() {
                @Override
                public void onResponse(Call<Courses> call, retrofit2.Response<Courses> response) {
                    Courses courses = response.body();
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
                                                            refreshPage();
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
                @Override
                public void onFailure(Call<Courses> call, Throwable t) {
                    Log.e("TAG", "onFailure: " + t.getMessage(), t);
                }
            });
            refreshPage();
            editTextDescriptionLectures.setText("");
            editTextTitileLectures.setText("");
            editTextLectureLectures.setText("");
            editTextQuizzName.setText("");
            editTextQuizzDescription.setText("");
            editTextQuestion.setText("");
            editTextCorrectAnswer.setText("");
            editTextQuestion1.setText("");
            editTextQuestion2.setText("");
            editTextQuestion3.setText("");
            editTextQuestion4.setText("");
            editTextGroups.clear();
            textInputLayoutGroups.clear();
            buttonGroups.clear();
            linearLayoutList.clear();
        }
        nameCourseSelected.setVisibility(VISIBLE);
        recycler_view_lecturer.setVisibility(VISIBLE);
        addLecture.setVisibility(VISIBLE);
        describeShow.setVisibility(VISIBLE);
        describeShow(view);
        describeShow(view);
        backHome.setVisibility(VISIBLE);
        linear_layout2.setVisibility(VISIBLE);
        linear_layoutasd.setVisibility(GONE);
    }
    public void addQuiz(View view) {
        addEditTextGroup(linear_layoutasd);
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
            textInputLayout.setPadding(0, 0, 0, 5);
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

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextGroups.remove(editTexts);
                linearLayout.removeView(layout);
            }
        });

        layout.addView(removeButton);
        linearLayout.addView(layout, linearLayout.indexOfChild(findViewById(R.id.buttons_layout)));
        scrollToBottom();
        linearLayoutList.add(layout);
        editTextGroups.add(editTexts);
        buttonGroups.add(removeButton);
        textInputLayoutGroups.add(textInputLayouts);
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
    public void addLecture(View view) {
        addbuttonID = true;
        linear_layout2.setVisibility(GONE);
        delete_id.setVisibility(INVISIBLE);
        nameCourseSelected.setVisibility(GONE);
        descriptionCourseSelected.setVisibility(GONE);
        recycler_view_lecturer.setVisibility(GONE);
        addLecture.setVisibility(GONE);
        describeShow.setVisibility(GONE);
        backHome.setVisibility(GONE);
        linear_layoutasd.setVisibility(VISIBLE);

        editTextDescriptionLectures.setText(" ");
        editTextTitileLectures.setText(" ");
        editTextLectureLectures.setText(" ");
        editTextQuizzName.setText(" ");
        editTextQuizzDescription.setText(" ");
        editTextQuestion.setText(" ");
        editTextCorrectAnswer.setText(" ");
        editTextQuestion1.setText(" ");
        editTextQuestion2.setText(" ");
        editTextQuestion3.setText(" ");
        editTextQuestion4.setText(" ");

        editTextGroups.clear();
        textInputLayoutGroups.clear();
        buttonGroups.clear();
    }
    public void backHome(View view) {
        intent = new Intent(CoursesActivity.this, HomeActivity.class);
        long receivedString = getIntent().getLongExtra("user_id",0);
        intent.putExtra("user_id", receivedString);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
    public void deleteCourse(View view) {
        long lecture_id = getIntent().getLongExtra("lecture_id", 0);
        long courseId = getIntent().getLongExtra("courseId", 0);
        LectureService lectureService = ApiClient.getClient().create(LectureService.class);
        CourseService courseService = ApiClient.getClient().create(CourseService.class);
        Call<Void> callLectureDelete = lectureService.deleteLecturesByLectureId(lecture_id);
        callLectureDelete.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Call<Object[]> callCountCourses = courseService.selectLecturesCounter(courseId);
                linear_layout2.setVisibility(VISIBLE);
                linear_layoutasd.setVisibility(GONE);
                addLecture.setVisibility(VISIBLE);
                describeShow.setVisibility(VISIBLE);
                backHome.setVisibility(VISIBLE);
                callCountCourses.enqueue(new Callback<Object[]>() {
                    @Override
                    public void onResponse(Call<Object[]> call, Response<Object[]> response) {
                        Object[] objects = response.body();
                        if((double)objects[0] == 0.0) {
                            Call<Void> callDeleteCourse = courseService.deleteCoursesByCourseId(courseId);
                            callDeleteCourse.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    intent = new Intent(CoursesActivity.this, HomeActivity.class);
                                    long receivedString = getIntent().getLongExtra("user_id",0);
                                    intent.putExtra("user_id", receivedString);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                }
                            });
                        }
                        else {
                            Call<List<Lectures>> callLecturesAll = lectureService.selectLecutreByCourseId(courseId);
                            callLecturesAll.enqueue(new Callback<List<Lectures>>() {
                                @Override
                                public void onResponse(Call<List<Lectures>> call, Response<List<Lectures>> response) {
                                    if (response.isSuccessful()) {
                                        List<Lectures> lecturesList = response.body();
                                        if (lecturesList.isEmpty()) {
                                            recycler_view_lecturer.setVisibility(View.GONE);
                                        } else {
                                            questionAdapter.updateData(lecturesList);
                                            nameCourseSelected.setVisibility(View.VISIBLE);
                                            recycler_view_lecturer.setVisibility(View.VISIBLE);
                                        }
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
                    }
                    @Override
                    public void onFailure(Call<Object[]> call, Throwable t) {
                    }
                });
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }
}