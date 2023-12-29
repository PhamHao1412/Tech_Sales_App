package com.example.macmavenapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macmavenapp.Adapter.CartAdapter;
import com.example.macmavenapp.Interface.CartItemInterface;
import com.example.macmavenapp.Model.Cart;
import com.example.macmavenapp.Model.User;
import com.example.macmavenapp.R;
import com.example.macmavenapp.Retrofit.CartAPI;
import com.example.macmavenapp.Somethings.ObjectSharedPreferences;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements CartItemInterface {
    CartAdapter cartAdapter;
    RecyclerView recyclerViewCart;
    Button btnCheckout;
    TextView tvTotalPrice, tvCountCart;

    ImageView ivHome, ivUser, ivCart, ivHistory;
    ConstraintLayout clCartIsEmpty, clCart;
    List<Cart> listCart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        AnhXa();
        LoadCart();
        appBarClick();
        btnCheckoutClick();
    }

    private void btnCheckoutClick() {
        btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, CheckOutActivity.class);
            startActivity(intent);
        });
    }

    private void appBarClick() {
        ivHome.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, MainActivity.class));
            finish();
        });
        ivUser.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, UserActivity.class));
            finish();
        });
        ivCart.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, CartActivity.class));
            finish();
        });

        ivHistory.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, OrderActivity.class));
            finish();
        });
    }

    private void LoadCart() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerViewCart = findViewById(R.id.view);
        recyclerViewCart.setLayoutManager(linearLayoutManager);
        User user = ObjectSharedPreferences.getSavedObjectFromPreference(CartActivity.this, "User", "MODE_PRIVATE", User.class);
        CartItemInterface cartItemInterface = this;
        //GET API
        CartAPI.cartAPI.cartOfUser(user.getId()).enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                listCart = response.body();
                if (listCart.isEmpty()){
                    clCartIsEmpty.setVisibility(View.VISIBLE);
                    clCart.setVisibility(View.GONE);
                }
                else{
                    cartAdapter = new CartAdapter(cartItemInterface, listCart, CartActivity.this);
                    recyclerViewCart.setAdapter(cartAdapter);

                    int Total= 0;
                    for(Cart y: listCart){
                        Total += y.getCount()*y.getProduct().getPrice();
                    }
                    Locale localeEN = new Locale("en", "EN");
                    NumberFormat en = NumberFormat.getInstance(localeEN);
                    tvTotalPrice.setText(en.format(Total));
//                    tvCountCart.setText(listCart.size()+"");
                }
            }
            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                Log.e("====", "Call API Cart of user fail");
            }
        });
    }
    private void AnhXa() {
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        ivHome = findViewById(R.id.ivHome);
        ivUser = findViewById(R.id.ivUser);
        ivCart = findViewById(R.id.ivCart);
        ivHistory = findViewById(R.id.ivHistory);
        clCart = findViewById(R.id.clCart);
        clCartIsEmpty = findViewById(R.id.clCartIsEmpty);
        btnCheckout = findViewById(R.id.btnCheckout);
    }

    @Override
    public void onClickUpdatePrice(int price) {
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        int total = Integer.parseInt(tvTotalPrice.getText().toString().replace(",",""));
        total += price;
        if (total ==0){
            clCartIsEmpty.setVisibility(View.VISIBLE);
            clCart.setVisibility(View.GONE);
        }
        tvTotalPrice.setText(en.format(total));
    }
}
