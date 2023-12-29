package com.example.macmavenapp.Activity;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macmavenapp.Adapter.ProductAdapter;
import com.example.macmavenapp.Adapter.ProductCapacityAdapter;
import com.example.macmavenapp.Adapter.ProductColorAdapter;
import com.example.macmavenapp.Adapter.SliderAdapter;
import com.example.macmavenapp.Model.Cart;
import com.example.macmavenapp.Model.Product;
import com.example.macmavenapp.Model.Specifications;
import com.example.macmavenapp.Model.User;
import com.example.macmavenapp.R;
import com.example.macmavenapp.Retrofit.CartAPI;
import com.example.macmavenapp.Retrofit.ProductAPI;
import com.example.macmavenapp.Somethings.ObjectSharedPreferences;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowDetailActivity extends AppCompatActivity {
    TextView tvTitle, tvdescription, tvPrice, tvTotalPrice, tvSold, tvAvailable, tvNumber, tvAddToCart,tvDecription2, tvSpecification2,
    tvOp_cpu, tvMemory, tvDisplay, tvBattery, tvConnect, tvDesign;
    ImageView ivMinus, ivPlus;
    Product product;
    private RecyclerView.Adapter adapter,adapter2;
    private RecyclerView recyclerViewColorList, recyclerViewCapacityList;
    ConstraintLayout clBack,specLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        product = (Product) getIntent().getSerializableExtra("product");
        AnhXa();
        LoadProduct();
        ivMinusClick();
        ivPlusClick();
        tvAddToCartClick();
        clBackClick();
        LoadColors();
        LoadCapacities();
        DescriptionClick();
    }

    private void clBackClick() {
        clBack.setOnClickListener(v -> {
            startActivity(new Intent(ShowDetailActivity.this, MainActivity.class));
        });
    }

    private void tvAddToCartClick() {
        tvAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = ObjectSharedPreferences.getSavedObjectFromPreference(ShowDetailActivity.this, "User", "MODE_PRIVATE", User.class);
                int count = parseInt(tvNumber.getText().toString());
                CartAPI.cartAPI.addToCart(user.getId(), product.getId(), count).enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        Cart cart = response.body();
                        if(cart !=null){
                            Toast.makeText(ShowDetailActivity.this.getApplicationContext(), "Thêm vào giỏ thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {
                        Toast.makeText(ShowDetailActivity.this.getApplicationContext(), "Thêm vào giỏ thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void LoadColors() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewColorList = findViewById(R.id.view1);
        recyclerViewColorList.setLayoutManager(linearLayoutManager);
        ProductAPI.productApi.getColor(product.getProduct_Name(),product.getCapacity()).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                List<String> newProductsList = response.body();
                Log.d("ffff", "Color: " + newProductsList);
                String selectedProductColor = product.getColor();

                adapter = new ProductColorAdapter(newProductsList, ShowDetailActivity.this,selectedProductColor,product);
                recyclerViewColorList.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("++++", t.getMessage());
                Log.e("====", "Call API Get New Products fail");
            }
        });
    }
    private void LoadCapacities() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCapacityList = findViewById(R.id.view2);
        recyclerViewCapacityList.setLayoutManager(linearLayoutManager);
        ProductAPI.productApi.getCapacities(product.getProduct_Name(),product.getColor()).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                List<String> newProductsList = response.body();
                String selectedProductCapacities = product.getCapacity();

                adapter2 = new ProductCapacityAdapter(newProductsList, ShowDetailActivity.this,selectedProductCapacities,product);
                recyclerViewCapacityList.setAdapter(adapter2);
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("++++", t.getMessage());
                Log.e("====", "Call API Get New Products fail");
            }
        });
    }


    private void LoadProduct() {
        LoadSpecification();
        LoadImage();
        tvTitle.setText(product.getProduct_Name()+" "+product.getCapacity());
        tvdescription.setText(product.getDescription());
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        tvPrice.setText(en.format(product.getPrice()));
        tvSold.setText(String.valueOf(product.getSold()));
        tvAvailable.setText(String.valueOf(product.getQuantity()));
        tvTotalPrice.setText(en.format(product.getPrice()));
        tvNumber.setText("1");
    }

    private void LoadImage() {
        SliderView sliderView = findViewById(R.id.imageSlider);

        SliderAdapter adapter = new SliderAdapter(ShowDetailActivity.this, product.getProductImage());

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }

    private void ivPlusClick() {
        ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = parseInt(tvNumber.getText().toString()) +1;
                if(number <= product.getQuantity()){
                    tvNumber.setText(String.valueOf(number));
                    Locale localeEN = new Locale("en", "EN");
                    NumberFormat en = NumberFormat.getInstance(localeEN);
                    tvTotalPrice.setText(en.format(product.getPrice()*number));
                }
            }
        });
    }
    private void LoadSpecification(){
        ProductAPI.productApi.getSpecifications(product.getId()).enqueue(new Callback<Specifications>() {
            @Override
            public void onResponse(Call<Specifications> call, Response<Specifications> response) {
               Specifications specifications = response.body();
                Log.d("ffff", "Specifications: "+specifications);
               tvOp_cpu.setText(specifications.getOp_cpu());
               tvMemory.setText(specifications.getMemory());
               tvDisplay.setText(specifications.getDisplay());
               tvBattery.setText(specifications.getBattery());
               tvConnect.setText(specifications.getConnect());
               tvDesign.setText(specifications.getDesign());
            }
            @Override
            public void onFailure(Call<Specifications> call, Throwable t) {
                Log.e("++++", t.getMessage());
                Log.e("====", "Call API Get Specifications fail");
            }
        });
    }
    private void DescriptionClick() {
        tvDecription2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(tvdescription.getVisibility() == View.VISIBLE){
                    tvdescription.setVisibility(View.GONE);
                    specLayout.setVisibility(View.VISIBLE);
                }
                else{
                    specLayout.setVisibility(View.GONE);
                    tvdescription.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private void ivMinusClick() {
        ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = parseInt(tvNumber.getText().toString()) - 1;
                if(number >= 1){
                    tvNumber.setText(String.valueOf(number));
                    Locale localeEN = new Locale("en", "EN");
                    NumberFormat en = NumberFormat.getInstance(localeEN);
                    tvTotalPrice.setText(en.format(product.getPrice()*number));
                }
            }
        });
    }

    private void AnhXa() {
        tvAddToCart = findViewById(R.id.tvAddToCart);
        tvTitle = findViewById(R.id.tvTitle);
        tvdescription = findViewById(R.id.tvDescription);
        tvPrice = findViewById(R.id.tvPrice);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvSold = findViewById(R.id.tvSold);
        tvAvailable = findViewById(R.id.tvAvailable);
        tvNumber = findViewById(R.id.tvNumber);
        ivMinus = findViewById(R.id.ivMinus);
        ivPlus = findViewById(R.id.ivPlus);
        clBack = findViewById(R.id.clBack);
        tvDecription2 = findViewById(R.id.tvDecription3);
        tvSpecification2 = findViewById(R.id.tvDecription3);
        specLayout = findViewById(R.id.specLayout);
        tvOp_cpu = findViewById(R.id.tvOp_cpu);
        tvMemory = findViewById(R.id.tvMemory);
        tvDisplay = findViewById(R.id.tvDisplay);
        tvBattery = findViewById(R.id.tvBattery);
        tvConnect = findViewById(R.id.tvConnect);
        tvDesign = findViewById(R.id.tvDesign);

    }
}
