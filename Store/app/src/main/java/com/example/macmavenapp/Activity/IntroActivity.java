package com.example.macmavenapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.macmavenapp.Model.User;
import com.example.macmavenapp.R;
import com.example.macmavenapp.Somethings.ObjectSharedPreferences;

public class IntroActivity extends AppCompatActivity {
    TextView tvStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToMainActivity();
            }
        }, 4000);
    }

    private void navigateToMainActivity() {
        User loggedInUser = ObjectSharedPreferences.getSavedObjectFromPreference(
                IntroActivity.this, "User", "MODE_PRIVATE", User.class);

        if (loggedInUser != null) {
            startActivity(new Intent(IntroActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(IntroActivity.this, LoginActivity.class));
        }

        finish(); // Kết thúc IntroActivity sau khi chuyển đến MainActivity hoặc LoginActivity
    }

}
