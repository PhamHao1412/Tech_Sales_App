package com.example.macmavenapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macmavenapp.Adapter.CheckOutAdapter;
import com.example.macmavenapp.Model.Address;
import com.example.macmavenapp.Model.Cart;
import com.example.macmavenapp.Model.Order;
import com.example.macmavenapp.Model.User;
import com.example.macmavenapp.Momo.Constant;
import com.example.macmavenapp.R;
import com.example.macmavenapp.Retrofit.CartAPI;
import com.example.macmavenapp.Retrofit.OrderAPI;
import com.example.macmavenapp.Somethings.ObjectSharedPreferences;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.momo.momo_partner.AppMoMoLib;
import vn.momo.momo_partner.MoMoParameterNamePayment;
//import vn.zalopay.sdk.Environment;
//import vn.zalopay.sdk.ZaloPaySDK;

public class CheckOutActivity extends AppCompatActivity {

    ImageView ivBack;
    TextView tvUserName, tvAddress, tvTotalPrice, tvPhoneNumber, tvChangeAddress, tvAddAddress, tvPayWithZaloPay, tvPayOnDelivery;
    Button btnPlaceOrder;
    ConstraintLayout constraintLayoutAddress, constraintLayoutNotAddress, placeOrder, placeOrderSuccess;

    RadioButton rbPayOnDelivery, rbPayWithZaloPay;

    RecyclerView.Adapter checkOutAdapter;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
//        ZaloPaySDK.init(2553, Environment.SANDBOX);
        AnhXa();
        LoadAddress();
        LoadProductItem();
        ivBackClick();
        constraintLayoutNotAddressClick();
        tvChangeAddressClick();
        btnPlaceOrderClick();
        radioButtonClick();
    }

    private void radioButtonClick() {
        tvPayOnDelivery.setOnClickListener(v -> {
            rbPayOnDelivery.setChecked(true);
        });
        tvPayWithZaloPay.setOnClickListener(v -> {
            rbPayWithZaloPay.setChecked(true);
        });
    }
    //Get token through MoMo app
    private void requestPayment(String amount) {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);

        Map<String, Object> eventValue = new HashMap<>();
        //client

        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME, Constant.merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put(MoMoParameterNamePayment.MERCHANT_CODE,  Constant.merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put(MoMoParameterNamePayment.AMOUNT,  amount);
        eventValue.put(MoMoParameterNamePayment.DESCRIPTION, Constant.description);

        //client Optional - bill info
        eventValue.put(MoMoParameterNamePayment.FEE, Constant.fee);
        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME_LABEL, Constant.merchantNameLabel);//mô tả đơn hàng - short description

        //client extra data
        eventValue.put(MoMoParameterNamePayment.REQUEST_ID,  Constant.merchantCode+"-"+ UUID.randomUUID().toString());
        eventValue.put(MoMoParameterNamePayment.PARTNER_CODE, Constant.merchantCode);
        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    String token = data.getStringExtra("data"); //Token response
                    if(token != null && !token.equals("")) {
                      newOrder("Pay with Momo");
                    } else {
                        Log.d("khong thanh cong",data.getStringExtra("message"));
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    // TOKEN FAIL
                    String message = data.getStringExtra("message") != null ? data.getStringExtra("message") : "Thất bại";
                    Toast.makeText(getApplicationContext(), "Payment failed: " + message, Toast.LENGTH_SHORT).show();
                } else if(data.getIntExtra("status", -1) == 2) {
                    // TOKEN FAIL
                    Toast.makeText(getApplicationContext(), "Payment failed: An error occurred", Toast.LENGTH_SHORT).show();
                } else {
                    // TOKEN FAIL
                    Toast.makeText(getApplicationContext(), "Payment failed: Unknown error", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Payment failed: Unknown error", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Payment failed: Unknown error", Toast.LENGTH_SHORT).show();
        }
    }

    private void btnPlaceOrderClick() {
        btnPlaceOrder.setOnClickListener(v -> {
            if (rbPayOnDelivery.isChecked()) {
                newOrder("Pay on Delivery");
            } else if (rbPayWithZaloPay.isChecked()) {
                String amount = tvTotalPrice.getText().toString().replace(",","");
                requestPayment(amount);
            }
            else {
                Toast.makeText(getApplicationContext(), "Please choose a payment method", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void newOrder(String method) {
        Address address = ObjectSharedPreferences.getSavedObjectFromPreference(CheckOutActivity.this, "address", "MODE_PRIVATE", Address.class);
        if (address == null) {
            Toast.makeText(CheckOutActivity.this.getApplicationContext(), "Please add your address before place order", Toast.LENGTH_SHORT).show();
        } else {
            User user = ObjectSharedPreferences.getSavedObjectFromPreference(CheckOutActivity.this, "User", "MODE_PRIVATE", User.class);
            OrderAPI.orderAPI.placeOrder(user.getId(), tvUserName.getText().toString(), tvPhoneNumber.getText().toString().replace("(", "").replace(")", ""), tvAddress.getText().toString(), method).enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    Order order = response.body();
                    if (order != null) {
                        placeOrder.setVisibility(View.GONE);
                        placeOrderSuccess.setVisibility(View.VISIBLE);
                        Button btnContinueShopping = findViewById(R.id.btnContinueShopping);
                        btnContinueShopping.setOnClickListener(v1 -> {
                            placeOrder.setVisibility(View.VISIBLE);
                            placeOrderSuccess.setVisibility(View.GONE);
                            startActivity(new Intent(CheckOutActivity.this, MainActivity.class));
                        });
                    }
                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {

                }
            });
        }
    }

    private void tvChangeAddressClick() {
        tvChangeAddress.setOnClickListener(v -> {
            startActivity(new Intent(CheckOutActivity.this, AddressActivity.class));
            finish();
        });
    }

    private void constraintLayoutNotAddressClick() {
        constraintLayoutNotAddress.setOnClickListener(v -> {
            startActivity(new Intent(CheckOutActivity.this, AddressActivity.class));
            finish();
        });
    }

    private void ivBackClick() {
        ivBack.setOnClickListener(v -> {
//            onBackPressed();
            startActivity(new Intent(CheckOutActivity.this, CartActivity.class));
            finish();
        });
    }

    private void LoadProductItem() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.view);
        recyclerView.setLayoutManager(linearLayoutManager);
        User user = ObjectSharedPreferences.getSavedObjectFromPreference(CheckOutActivity.this, "User", "MODE_PRIVATE", User.class);
        CartAPI.cartAPI.cartOfUser(user.getId()).enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                List<Cart> listCart = response.body();
//                User user = ObjectSharedPreferences.getSavedObjectFromPreference(CheckOutActivity.this, "User", "MODE_PRIVATE", User.class);

                checkOutAdapter = new CheckOutAdapter(listCart, CheckOutActivity.this);
                recyclerView.setAdapter(checkOutAdapter);

                //load total price
                int Total = 0;
                for (Cart y : listCart) {
                    Total += y.getCount() * y.getProduct().getPrice();
                }
                Locale localeEN = new Locale("en", "EN");
                NumberFormat en = NumberFormat.getInstance(localeEN);
                tvTotalPrice.setText(en.format(Total));
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                Log.e("====", "Call API Cart of user fail");
            }
        });
    }

    private void LoadAddress() {
        Address address = ObjectSharedPreferences.getSavedObjectFromPreference(CheckOutActivity.this, "address", "MODE_PRIVATE", Address.class);
//        Log.e("====", address.toString());
        if (address == null) {
            constraintLayoutAddress.setVisibility(View.GONE);
            constraintLayoutNotAddress.setVisibility(View.VISIBLE);
        } else {
            tvPhoneNumber.setText("(" + address.getPhoneNumber() + ")");
            tvUserName.setText(address.getFullName());
            tvAddress.setText(address.getAddress());
        }
    }

    private void AnhXa() {
        ivBack = findViewById(R.id.ivBack);
        tvUserName = findViewById(R.id.tvUserName);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvAddress = findViewById(R.id.tvAddress);
        tvChangeAddress = findViewById(R.id.tvChangeAddress);
        tvAddAddress = findViewById(R.id.tvAddAddress);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        constraintLayoutAddress = findViewById(R.id.constraintLayoutAddress);
        constraintLayoutNotAddress = findViewById(R.id.constraintLayoutNotAddress);
        placeOrderSuccess = findViewById(R.id.placeOrderSuccess);
        placeOrder = findViewById(R.id.placeOrder);
        tvPayWithZaloPay = findViewById(R.id.tvPayWithZaloPay);
        tvPayOnDelivery = findViewById(R.id.tvPayOnDelivery);
        rbPayWithZaloPay = findViewById(R.id.rbPayWithZaloPay);
        rbPayOnDelivery = findViewById(R.id.rbPayOnDelivery);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        ZaloPaySDK.getInstance().onResult(intent);
    }
}