package com.example.macmavenapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macmavenapp.Adapter.OrderAdapter;
import com.example.macmavenapp.Model.Order;
import com.example.macmavenapp.Model.User;
import com.example.macmavenapp.R;
import com.example.macmavenapp.Retrofit.OrderAPI;
import com.example.macmavenapp.Somethings.ObjectSharedPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PayOnDeliveryFragment extends Fragment {
    RecyclerView rvAllOrder;
    OrderAdapter orderAdapter;
    List<Order> orderList;

    public PayOnDeliveryFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pay_on_delivery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AnhXa();
        LoadData();
    }

    private void LoadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        rvAllOrder.setLayoutManager(linearLayoutManager);
        User user = ObjectSharedPreferences.getSavedObjectFromPreference(getContext(), "User", "MODE_PRIVATE", User.class);
        OrderAPI.orderAPI.getOrderByUserIdAndPaymentMethod(user.getId(), "Pay on Delivery").enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
//                Log.e("=====", response.body().toString());
                orderList = response.body();
                orderAdapter = new OrderAdapter(orderList, getContext().getApplicationContext());
                rvAllOrder.setAdapter(orderAdapter);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }

    private void AnhXa() {
        rvAllOrder = getView().findViewById(R.id.rvPayOnDelivery);
    }
}