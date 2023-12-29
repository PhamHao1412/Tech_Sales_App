package com.example.macmavenapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.macmavenapp.Model.User;
import com.example.macmavenapp.R;
import com.example.macmavenapp.Retrofit.UserAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    EditText etUserName, etPassword, etEmail, etRePassword, etFullName;
    Button btnSignUp;

    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        AnhXa();
        btnSignUpClick();
        tvLoginClick();
    }

    private void tvLoginClick() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }

    private void btnSignUpClick() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etUserName = findViewById(R.id.etUserName);
                etFullName = findViewById(R.id.etNewPass);
                etEmail = findViewById(R.id.etEmail);
                etPassword = findViewById(R.id.etPassword);
                etRePassword = findViewById(R.id.etRePassword);

                if (TextUtils.isEmpty(etUserName.getText().toString())){
                    etUserName.setError("Please enter your username");
                    etUserName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(etFullName.getText().toString())){
                    etFullName.setError("Please enter your full name");
                    etFullName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(etEmail.getText().toString())){
                    etEmail.setError("Please enter your email");
                    etEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(etPassword.getText().toString())){
                    etPassword.setError("Please enter your username");
                    etPassword.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(etRePassword.getText().toString())){
                    etRePassword.setError("Please enter your password");
                    etRePassword.requestFocus();
                    return;
                }

                String username = etUserName.getText().toString();
                String fullname = etFullName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String repassword = etRePassword.getText().toString();

                if (!password.equals(repassword)){
                    etRePassword.setError("Password and RePassword not match");
                    etRePassword.requestFocus();
                    return;
                }
                else {

                    UserAPI.userApi.SignUp(username, fullname, email, password).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            User user = response.body();
                            Log.e("ffff", "Thành công");
                            Log.e("ffff", user.toString());
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        }
                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.e("ffff", "Kết nối API thất bại");

                        }
                    });
                }
            }
        });
    }

    private void AnhXa() {
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLogin = findViewById(R.id.tvLogin1);
    }


}