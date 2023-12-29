package com.example.macmavenapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.macmavenapp.Activity.ShowDetailActivity;
import com.example.macmavenapp.Model.Cart;
import com.example.macmavenapp.Model.Product;
import com.example.macmavenapp.Model.User;
import com.example.macmavenapp.R;
import com.example.macmavenapp.Retrofit.CartAPI;
import com.example.macmavenapp.Somethings.ObjectSharedPreferences;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<Product> Products;

    Context context;
    public ProductAdapter(List<Product> products, Context context) {
        this.Products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_products, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = Products.get(position);
        holder.title.setText(Products.get(position).getProduct_Name() + " "+ Products.get(position).getCapacity());
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        holder.fee.setText(en.format(Products.get(position).getPrice()));
        holder.sold.setText("Sold: " + String.valueOf(Products.get(position).getSold()));
        Glide.with(holder.itemView.getContext())
                .load(Products.get(position).getProductImage().get(0).getUrl_Image())
                .into(holder.pic);
        holder.addBtn.setOnClickListener(v -> {
            User user = ObjectSharedPreferences.getSavedObjectFromPreference(context, "User", "MODE_PRIVATE", User.class);
            CartAPI.cartAPI.addToCart(user.getId(), product.getId(), 1).enqueue(new Callback<Cart>() {
                @Override
                public void onResponse(Call<Cart> call, Response<Cart> response) {
                    Cart cart = response.body();
                    Log.d("debug", "Cart: "+cart.getProduct());
                    Log.d("debug", "Cart: "+cart);

                    if(cart !=null){
                        Toast.makeText(context.getApplicationContext(), "Thêm vào giỏ thành công", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context.getApplicationContext(), "Thêm vào giỏ thất bại", Toast.LENGTH_SHORT).show();

                    }
                }
                @Override
                public void onFailure(Call<Cart> call, Throwable t) {
                    Toast.makeText(context.getApplicationContext(), "Call API Add to cart fail", Toast.LENGTH_SHORT).show();
                }
            });
        });

        holder.itemView.setOnClickListener(v ->{
            Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
            intent.putExtra("product", product);
            holder.itemView.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return Products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, fee, sold;
        ImageView pic;
        ImageView addBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            sold = itemView.findViewById(R.id.sold);
            pic = itemView.findViewById(R.id.ivImage);
            fee = itemView.findViewById(R.id.fee);
            addBtn = itemView.findViewById(R.id.addBtn);
        }
    }
}
