package com.example.macmavenapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.macmavenapp.Adapter.OrderFragmentAdapter;
import com.example.macmavenapp.Model.Order;
import com.example.macmavenapp.Model.User;
import com.example.macmavenapp.R;
import com.example.macmavenapp.Retrofit.OrderAPI;
import com.example.macmavenapp.Somethings.ObjectSharedPreferences;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    OrderFragmentAdapter orderFragmentAdapter;
    ImageView ivHome, ivUser, ivCart, ivHistory;

    ConstraintLayout clOrder, clEmptyOrder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        AnhXa();
        appBarClick();
        FragmentManager fragmentManager = getSupportFragmentManager();
        orderFragmentAdapter = new OrderFragmentAdapter(fragmentManager, getLifecycle());
        CheckEmpty();
        viewPager2.setAdapter(orderFragmentAdapter);
        tabLayout.addTab(tabLayout.newTab().setText("Pending"));
        tabLayout.addTab(tabLayout.newTab().setText("Processing"));
        tabLayout.addTab(tabLayout.newTab().setText("Shipped"));
        tabLayout.addTab(tabLayout.newTab().setText("Completed"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    private void CheckEmpty() {
        User user = ObjectSharedPreferences.getSavedObjectFromPreference(OrderActivity.this, "User", "MODE_PRIVATE", User.class);
        OrderAPI.orderAPI.getOrderByUserId(user.getId()).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if(response.body().isEmpty()){
                    clOrder.setVisibility(View.GONE);
                    clEmptyOrder.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }

    private void appBarClick() {
        ivHome.setOnClickListener(v -> {
            startActivity(new Intent(OrderActivity.this, MainActivity.class));
            finish();
        });
        ivUser.setOnClickListener(v -> {
            startActivity(new Intent(OrderActivity.this, UserActivity.class));
            finish();
        });
        ivCart.setOnClickListener(v -> {
            startActivity(new Intent(OrderActivity.this, CartActivity.class));
            finish();
        });

        ivHistory.setOnClickListener(v -> {
            startActivity(new Intent(OrderActivity.this, OrderActivity.class));
            finish();
        });
    }

    private void AnhXa() {
        ivHome = findViewById(R.id.ivHome);
        ivUser = findViewById(R.id.ivUser); 
        ivCart = findViewById(R.id.ivCart);
        ivHistory = findViewById(R.id.ivHistory);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);
        clOrder = findViewById(R.id.clOrder);
        clEmptyOrder = findViewById(R.id.clEmptyOrder);
    }
}
