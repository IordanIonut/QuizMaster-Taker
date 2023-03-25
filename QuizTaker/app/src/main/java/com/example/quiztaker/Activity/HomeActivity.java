package com.example.quiztaker.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quiztaker.Adapter.CourseAdapter;
import com.example.quiztaker.Model.Courses;
import com.example.quiztaker.Model.Users;
import com.example.quiztaker.R;
import com.example.quiztaker.Service.ApiClient;
import com.example.quiztaker.Service.CourseService;
import com.example.quiztaker.Service.UserService;
import com.example.quiztaker.Utility.DarkModePrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

import static android.view.View.*;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private View mAppBarMain, mAppBarCourses, mAppBarAdd, mAppBarUser;
    private ScrollView scroll_view;
    private Intent intent;
    private Users users = new Users();
    private TextView NameView, emailView, numberView, finalAll, progresAll, startAll;
    private RecyclerView  courseLast6, courseAll, courseAbonement;
    private ScrollView scroll_view1234, scroll_view12345;
    private Handler handler = new Handler();
    private Runnable refreshRunnable;
    private LinearLayoutManager layoutManager;
    private CourseAdapter courseAdapterAbonement, courseAdapter;
    private SearchView searchNotComplete, searchComplete, searchNotAbonament;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            CourseService courseService = ApiClient.getClient().create(CourseService.class);
            UserService userService = ApiClient.getClient().create(UserService.class);
            switch (item.getItemId()) {
                case R.id.navigationMyCourses:
                    mAppBarMain.setVisibility(GONE);
                    mAppBarCourses.setVisibility(VISIBLE);
                    mAppBarAdd.setVisibility(GONE);
                    mAppBarUser.setVisibility(GONE);
                    Call<List<Courses>> callCoursesNotAbonament = courseService.selectCoursesNotAbonament(users.getUser_id(),"");
                    callCoursesNotAbonament.enqueue(new Callback<List<Courses>>() {
                        @Override
                        public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                            if (response.isSuccessful()) {
                                List<Courses> userCoursesList = response.body();
                                for (Courses course : userCoursesList)
                                courseAbonement.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                courseAbonement.setAdapter(new CourseAdapter(getApplicationContext(), userCoursesList, users.getUser_id(), "no"));
                            } else {
                                Log.d("TAG", "onResponse: Error code " + response.code());
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
                    Call<List<Courses>> callCoursesNotCompleted = courseService.selectCoursesNotCompletedName(users.getUser_id(),"");
                    callCoursesNotCompleted.enqueue(new Callback<List<Courses>>() {
                        @Override
                        public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                            if (response.isSuccessful()) {
                                List<Courses> userCoursesList = response.body();
                                for (Courses course : userCoursesList)
                                courseLast6.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                courseLast6.setAdapter(new CourseAdapter(getApplicationContext(), userCoursesList, users.getUser_id(), "no"));
                            } else {
                                Log.d("TAG", "onResponse: Error code " + response.code());
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
                    Call<List<Courses>> callCoursesCompleted = courseService.selectCursusesComplete(users.getUser_id(),"");
                    callCoursesCompleted.enqueue(new Callback<List<Courses>>() {
                        @Override
                        public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                            if (response.isSuccessful()) {
                                List<Courses> userCoursesList = response.body();
                                for (Courses course : userCoursesList)
                                courseAll.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                courseAll.setAdapter(new CourseAdapter(getApplicationContext(), userCoursesList, users.getUser_id(), "yes"));
                            } else {
                                Log.d("TAG", "onResponse: Error code " + response.code());
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Courses>> call, Throwable t) {
                            Log.e("TAG", "onFailure: " + t.getMessage(), t);
                        }
                    });
                    break;
                case  R.id.navigationPerson:
                    mAppBarMain.setVisibility(GONE);
                    mAppBarCourses.setVisibility(GONE);
                    mAppBarAdd.setVisibility(GONE);
                    mAppBarUser.setVisibility(VISIBLE);
                    Call<List<Courses>> call1 = courseService.selectCursusesComplete(users.getUser_id(),"");
                    call1.enqueue(new Callback<List<Courses>>() {
                        @Override
                        public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                            if (response.isSuccessful()) {
                                List<Courses> userCoursesList = response.body();
                                finalAll.setText(userCoursesList.size()+"");
                            } else {
                                Log.d("TAG", "onResponse: Error code " + response.code());
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Courses>> call, Throwable t) {
                            Log.e("TAG", "onFailure: " + t.getMessage(), t);
                        }
                    });
                    Call<List<Courses>> call2 = courseService.selectCoursesNotCompletedName(users.getUser_id(),"");
                    call2.enqueue(new Callback<List<Courses>>() {
                        @Override
                        public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                            if (response.isSuccessful()) {
                                List<Courses> userCoursesList = response.body();
                                progresAll.setText(userCoursesList.size()+"");
                            } else {
                                Log.d("TAG", "onResponse: Error code " + response.code());
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Courses>> call, Throwable t) {
                            Log.e("TAG", "onFailure: " + t.getMessage(), t);
                        }
                    });
                    Call<List<Courses>> call3 = courseService.selectCourseStarted(users.getUser_id());
                    call3.enqueue(new Callback<List<Courses>>() {
                        @Override
                        public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                            if (response.isSuccessful()) {
                                List<Courses> userCoursesList = response.body();
                                startAll.setText(userCoursesList.size()+"");
                            } else {
                                Log.d("TAG", "onResponse: Error code " + response.code());
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Courses>> call, Throwable t) {
                            Log.e("TAG", "onFailure: " + t.getMessage(), t);
                        }
                    });
                    Call<Users> callUser = userService.selectUserByUserId(users.getUser_id());
                    callUser.enqueue(new Callback<Users>() {
                        @Override
                        public void onResponse(Call<Users> call, Response<Users> response) {
                            if(response.isSuccessful()) {
                                Users user = response.body();
                                NameView.setText(user.getUser_name());
                                emailView.setText(user.getEmail());
                                numberView.setText(user.getPhone_number());
                            }
                        }
                        @Override
                        public void onFailure(Call<Users> call, Throwable t) {

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

        intent = getIntent();
        UserService userService = ApiClient.getClient().create(UserService.class);
        if(getIntent().getLongExtra("user_id",0) == 0) {
            String receivedString = intent.getStringExtra("STRING_KEY");
            Call<String> callToLogin = userService.userToken(receivedString);
            callToLogin.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        String string = response.body();
                        users.setUser_id(Long.valueOf(string));
                        filterDataNotComplete("");
                        filterDataNotAbonament("");
                        filterDataComplete("");
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }else{
            users.setUser_id(getIntent().getLongExtra("user_id",0));
        }
        if(new DarkModePrefManager(this).isNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        mAppBarMain = findViewById(R.id.content_main);
        mAppBarCourses = findViewById(R.id.app_bar_courses);
        mAppBarAdd = findViewById(R.id.serch_main);
        mAppBarUser = findViewById(R.id.app_bar_user);

        scroll_view = findViewById(R.id.scroll_view);

        courseLast6 = findViewById(R.id.recycler_view_now);
        courseAll = findViewById(R.id.recycler_view_all);
        courseAbonement = findViewById(R.id.recycler_view_all1);

        NameView = findViewById(R.id.NameView);
        emailView = findViewById(R.id.emailView);
        numberView = findViewById(R.id.numberView);
        finalAll = findViewById(R.id.finalAll);
        progresAll = findViewById(R.id.progresAll);
        startAll = findViewById(R.id.startAll);

        searchNotComplete = findViewById(R.id.searchNotComplete);
        searchComplete = findViewById(R.id.searchComplete);
        searchNotAbonament = findViewById(R.id.searchNotAbonament);

        searchNotComplete.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterDataNotComplete(newText);
                return true;
            }
        });
        searchComplete.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterDataComplete(newText);
                return true;
            }
        });
        searchNotAbonament.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterDataNotAbonament(newText);
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
        bottomNavigationView.setSelectedItemId(R.id.navigationHome);

        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.nav_dark_mode);

        DarkModePrefManager darkModePrefManager = new DarkModePrefManager(this);
        if (darkModePrefManager.isNightMode()) {
            menuItem.setIcon(R.drawable.outline_light_mode_black_24dp);
        }
        else {
            menuItem.setIcon(R.drawable.outline_dark_mode_black_24dp);
        }

    }
    private void filterDataNotAbonament(String query) {
        CourseService courseService = ApiClient.getClient().create(CourseService.class);
        Call<List<Courses>> callCoursesNotCompleted = courseService.selectCoursesNotAbonament(users.getUser_id(), query);
        callCoursesNotCompleted.enqueue(new Callback<List<Courses>>() {
            @Override
            public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                if (response.isSuccessful()) {
                    List<Courses> userCoursesList = response.body();
                    courseAbonement.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    courseAbonement.setAdapter(new CourseAdapter(getApplicationContext(), userCoursesList, users.getUser_id(), "no"));
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
    private void filterDataComplete(String query) {
        CourseService courseService = ApiClient.getClient().create(CourseService.class);
        Call<List<Courses>> callCoursesNotCompleted = courseService.selectCursusesComplete(users.getUser_id(), query);
        callCoursesNotCompleted.enqueue(new Callback<List<Courses>>() {
            @Override
            public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                if (response.isSuccessful()) {
                    List<Courses> userCoursesList = response.body();
                    courseAll.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    courseAll.setAdapter(new CourseAdapter(getApplicationContext(), userCoursesList, users.getUser_id(), "yes"));
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
    private void filterDataNotComplete(String query) {
        CourseService courseService = ApiClient.getClient().create(CourseService.class);
        Call<List<Courses>> callCoursesNotCompleted = courseService.selectCoursesNotCompletedName(users.getUser_id(), query);
        callCoursesNotCompleted.enqueue(new Callback<List<Courses>>() {
            @Override
            public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                if (response.isSuccessful()) {
                    List<Courses> userCoursesList = response.body();
                    courseLast6.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    courseLast6.setAdapter(new CourseAdapter(getApplicationContext(), userCoursesList, users.getUser_id(), "no"));
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(refreshRunnable);
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
}