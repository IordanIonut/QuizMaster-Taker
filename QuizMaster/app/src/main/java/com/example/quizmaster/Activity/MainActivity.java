package com.example.quizmaster.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizmaster.Model.LoginRequest;
import com.example.quizmaster.Model.LoginResponse;
import com.example.quizmaster.Model.Users;
import com.example.quizmaster.R;
import com.example.quizmaster.Service.ApiClient;
import com.example.quizmaster.Service.UserService;
import com.example.quizmaster.Utility.OnSwipeTouchListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int HOME_PAGE = 0;
    private static final int LOGIN_PAGE = 1;
    private static final int REGISTER_PAGE = 2;
    private int currentPage = HOME_PAGE;
    private View homeView;
    private View loginView;
    private View registerView;
    private Button register_btn, login_btn;
    private EditText editTextName, editTextEmail, editTextPhone, editTextPassword,  editTextEmail1,  editTextPassword1;
    private  String name, email, phone, password, role, email1, password1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeView = findViewById(R.id.layout_home);
        loginView = findViewById(R.id.layout_login);
        registerView = findViewById(R.id.layout_register);
        register_btn = findViewById(R.id.register_btn);
        login_btn = findViewById(R.id.login_btn);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);

        editTextEmail1 = findViewById(R.id.editTextEmail1);
        editTextPassword1 = findViewById(R.id.editTextPassword1);

        LinearLayout layout = findViewById(R.id.linearLayout);
        layout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                if (currentPage == HOME_PAGE) {
                    navigateToPage(LOGIN_PAGE);
                } else if (currentPage == REGISTER_PAGE) {
                    navigateToPage(HOME_PAGE);
                }
            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                if (currentPage == HOME_PAGE) {
                    navigateToPage(REGISTER_PAGE);
                } else if (currentPage == LOGIN_PAGE) {
                    navigateToPage(HOME_PAGE);
                }
            }
        });
    }
    private void navigateToPage(int page) {
        switch (page) {
            case HOME_PAGE:
                currentPage = HOME_PAGE;
                homeView.setVisibility(View.VISIBLE);
                loginView.setVisibility(View.GONE);
                registerView.setVisibility(View.GONE);
                break;
            case LOGIN_PAGE:
                currentPage = LOGIN_PAGE;
                homeView.setVisibility(View.GONE);
                loginView.setVisibility(View.VISIBLE);
                registerView.setVisibility(View.GONE);
                break;
            case REGISTER_PAGE:
                currentPage = REGISTER_PAGE;
                homeView.setVisibility(View.GONE);
                loginView.setVisibility(View.GONE);
                registerView.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
    public void Register(View view) {
        name = editTextName.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        phone = editTextPhone.getText().toString().trim();
        role = "teacher";
        UserService userService = ApiClient.getClient().create(UserService.class);

        if (TextUtils.isEmpty(name)) {
            editTextName.setError("The name is mandatory!");
            return;
        }
        else if (name.length() < 6) {
            editTextName.setError("The name needs to have minimum 6 characters!");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email address is mandatory!");
            return;
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("The email address is invalid!");
            return;
        }
        else if (!email.endsWith("@yahoo.com") && !email.endsWith("@gmail.com")) {
            editTextEmail.setError("The email address domain is not supported!");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            editTextPhone.setError("Phone number is mandatory!");
            return;
        }
        else if (phone.length() < 10 || phone.length() > 12) {
            editTextPhone.setError("Phone number should be between 10 and 12 digits!");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("The password is mandatory!");
            return;
        }
        else if (!password.matches("^(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.*[a-zA-Z]).{8,}$")) {
            editTextPassword.setError("The password must contain at least one digit and a special character (!,@,#,$,%,&,*,(,),-_,+={},[],|,;,:,\\\",',,,.,/ or ?) and have at least 8 characters!");
            return;
        }
        else if (password.length() < 8) {
            editTextPassword.setError("The password needs to have minimum 8 characters!");
            return;
        }

        Call<Users> calll = userService.findByEmail(email);
        Call<Users> callll = userService.findByPhone_number(phone);

        calll.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> calll, retrofit2.Response<Users> response) {
                if(response.isSuccessful()) {
                    Users user = response.body();
                    if(user != null) {
                        Toast.makeText(MainActivity.this, "Sorry, but this email is already registered. Please try with a different email or recover your password if you have forgotten it.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Users> calll, Throwable t) {
              //  Toast.makeText(MainActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
            }
        });

        callll.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> callll, retrofit2.Response<Users> response) {
                if(response.isSuccessful()) {
                    Users user = response.body();
                    if (user != null) {
                        Toast.makeText(MainActivity.this, "Sorry, but this phone number is already registered. Please try with a different phone number or recover your account if you have forgotten your login details.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Users> callll, Throwable t) {
                //Toast.makeText(MainActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
            }
        });

        Users user = new Users(email, password, phone, role, name);

        Call<Users> call = userService.createUser(user);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, retrofit2.Response<Users> response) {
                //Toast.makeText(MainActivity.this, "Error creating user", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(MainActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                navigateToPage(LOGIN_PAGE);
            }
        });
    }
    public void Login(View view) {
        email1 = editTextEmail1.getText().toString().trim();
        password1 = editTextPassword1.getText().toString().trim();
        UserService userService = ApiClient.getClient().create(UserService.class);

        if (TextUtils.isEmpty(email1)) {
            editTextEmail1.setError("Email address is mandatory!");
            return;
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
            editTextEmail1.setError("The email address is invalid!");
            return;
        }
        else if (!email1.endsWith("@yahoo.com") && !email1.endsWith("@gmail.com")) {
            editTextEmail1.setError("The email address domain is not supported!");
            return;
        }

        if (TextUtils.isEmpty(password1)) {
            editTextPassword1.setError("The password is mandatory!");
            return;
        }
        else if (!password1.matches("^(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.*[a-zA-Z]).{8,}$")) {
            editTextPassword1.setError("The password must contain at least one digit and a special character (!,@,#,$,%,&,*,(,),-_,+={},[],|,;,:,\\\",',,,.,/ or ?) and have at least 8 characters!");
            return;
        }
        else if (password1.length() < 8) {
            editTextPassword1.setError("The password needs to have minimum 8 characters!");
            return;
        }

        Call<Users> calll = userService.findByEmail(email1);
        calll.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> calll, retrofit2.Response<Users> response) {
                if(response.isSuccessful()) {
                    Users user = response.body();
                    if(user != null) {
                    }
                    else{
                        Toast.makeText(MainActivity.this, "This email is wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Users> calll, Throwable t) {
                //  Toast.makeText(MainActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
            }
        });

        LoginRequest loginRequest = new LoginRequest(email1, password1, "teacher");
        Call<LoginResponse> call = userService.authenticateUser(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    String token = loginResponse.getToken();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("STRING_KEY", token);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid password, please check the password!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


