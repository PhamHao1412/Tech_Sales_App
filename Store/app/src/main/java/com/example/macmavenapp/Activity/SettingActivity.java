package com.example.macmavenapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.macmavenapp.R;

public class SettingActivity extends AppCompatActivity {

    ImageView ivHome, ivUser, ivCart, ivHistory, ivSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        AnhXa();
        appBarClick();
    }

    private void appBarClick() {
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, MainActivity.class));
                finish();
            }
        });
        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, UserActivity.class));
                finish();
            }
        });
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, CartActivity.class));
                finish();
            }
        });

        ivHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, OrderActivity.class));
                finish();
            }
        });
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, SettingActivity.class));
                finish();
            }
        });
    }
    private void AnhXa() {
        ivHome = findViewById(R.id.ivHome);
        ivUser = findViewById(R.id.ivUser);
        ivCart = findViewById(R.id.ivCart);
        ivHistory = findViewById(R.id.ivHistory);
        ivSetting = findViewById(R.id.ivSetting);
    }
}
