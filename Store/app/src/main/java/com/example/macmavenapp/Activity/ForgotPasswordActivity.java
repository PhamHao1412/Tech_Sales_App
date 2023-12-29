package com.example.macmavenapp.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.macmavenapp.R;
import com.example.macmavenapp.Retrofit.UserAPI;
import com.fraggjkee.smsconfirmationview.SmsConfirmationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText etUserName, etNewPass, etReNewPass;
    Button btnSubmit, btnSubmitVerification, btnSubmitPassword;
    ConstraintLayout clForgotPassword, clVerification, clSetNewPassword;
    SmsConfirmationView smsConfirmationView;
    TextView tvLogin1, tvLogin2, tvLogin3, tvUserNameNotCorrect, tvCodeNotCorrect,tvPasswordNotMatch;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        AnhXa();
        btnSubmitClick();
        tvLogin1Click();
    }

    private void tvLogin1Click() {
        tvLogin1.setOnClickListener(v -> {
            startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
        });
    }

    private void btnSubmitClick() {
        btnSubmit.setOnClickListener(view -> {
            String user_id = etUserName.getText().toString();
            if (user_id.isEmpty()){
                Toast.makeText(this, "Please enter your user name!", Toast.LENGTH_SHORT).show();
                etUserName.requestFocus();
                return;
            }
            ProgressDialog progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
            progressDialog.setMessage("Checking..."); // Setting Message
            progressDialog.setTitle("Forgot Password"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
            UserAPI.userApi.forgotPassword(user_id).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String codeForgot = response.body();
//                    Log.e("===", codeForgot);
                    progressDialog.dismiss();
                    if(response.isSuccessful()){
                        Log.e("===", codeForgot);
                        clForgotPassword.setVisibility(View.GONE);
                        clVerification.setVisibility(View.VISIBLE);
//                        smsConfirmationView.requestFocus();
                        smsConfirmationView.setOnChangeListener((code, isComplete) -> {
                            if(isComplete==true){
                                if(code.equals(codeForgot)){
                                    clVerification.setVisibility(View.GONE);
                                    clSetNewPassword.setVisibility(View.VISIBLE);
                                    btnSubmitPassword.setOnClickListener(v -> {
                                        String newPass = etNewPass.getText().toString();
                                        String reNewPass = etReNewPass.getText().toString();
                                        if (newPass.equals(reNewPass) && !newPass.isEmpty()){
                                            UserAPI.userApi.forgotNewPass(user_id, codeForgot, newPass).enqueue(new Callback<String>() {
                                                @Override
                                                public void onResponse(Call<String> call, Response<String> response) {
                                                    if (response.isSuccessful()){
                                                        Dialog dialog = new Dialog(ForgotPasswordActivity.this);
                                                        dialog.setContentView(R.layout.dialog_forgot_password_success);
                                                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                                        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                        dialog.getWindow().setGravity(Gravity.BOTTOM);
                                                        dialog.setCancelable(false);
//                                                        startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                                                        dialog.show();
                                                        Button btnLogin = dialog.findViewById(R.id.btnChangePassword);
                                                        btnLogin.setOnClickListener(v1 -> {
                                                            startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                                                        });
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<String> call, Throwable t) {

                                                }
                                            });
                                        }
                                        else{
                                            tvPasswordNotMatch.setVisibility(View.VISIBLE);
                                            Toast.makeText(ForgotPasswordActivity.this, "Password and confirm password do not match!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    tvLogin3.setOnClickListener(v -> {
                                        startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                                    });
                                }
                                else{
                                    tvCodeNotCorrect.setVisibility(View.VISIBLE);
                                    Toast.makeText(ForgotPasswordActivity.this, "Your code is not correct!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        tvLogin2.setOnClickListener(v -> {
                            startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                        });
                        ivBack.setOnClickListener(v -> {
                            tvUserNameNotCorrect.setVisibility(View.GONE);
                            etUserName.setText(null);
                            clVerification.setVisibility(View.GONE);
                            clForgotPassword.setVisibility(View.VISIBLE);
                        });
                    }
                    else{
                        tvUserNameNotCorrect.setVisibility(View.VISIBLE);
                        Toast.makeText(ForgotPasswordActivity.this, "Your username is not correct!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("===++", t.getMessage());
                    progressDialog.dismiss();
                }
            });
        });
    }

    private void AnhXa() {
        //Anhxa clForgotPassword
        etUserName = findViewById(R.id.etUserName);
        btnSubmit = findViewById(R.id.btnSubmit);
        clForgotPassword = findViewById(R.id.clForgotPassword);
        clVerification = findViewById(R.id.clVerification);
        clSetNewPassword = findViewById(R.id.clSetNewPassword);
        tvLogin1 = findViewById(R.id.tvLogin1);
        tvUserNameNotCorrect = findViewById(R.id.tvUserNameNotCorrect);
        //Anh xa clVerification
        btnSubmitVerification= findViewById(R.id.btnSubmitVerification);
        smsConfirmationView = findViewById(R.id.smsCodeView);
        tvLogin2 = findViewById(R.id.tvLogin2);
        ivBack = findViewById(R.id.ivBack);
        tvCodeNotCorrect = findViewById(R.id.tvCodeNotCorrect);
        //Anh xa clSetNewPassword
        etNewPass = findViewById(R.id.etNewPass);
        etReNewPass = findViewById(R.id.etReNewPass);
        btnSubmitPassword = findViewById(R.id.btnSubmitPassword);
        tvLogin3 = findViewById(R.id.tvLogin3);
        tvPasswordNotMatch = findViewById(R.id.tvPasswordNotMatch);
    }
}
