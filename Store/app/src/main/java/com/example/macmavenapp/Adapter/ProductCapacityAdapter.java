package com.example.macmavenapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macmavenapp.Activity.ShowDetailActivity;
import com.example.macmavenapp.Model.Product;
import com.example.macmavenapp.R;
import com.example.macmavenapp.Retrofit.ProductAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCapacityAdapter  extends RecyclerView.Adapter<ProductCapacityAdapter.ViewHolder> {
    List<String> Capacities;
    Context context;
    private String selectedCapacity;
    Product product;


    public ProductCapacityAdapter(List<String> capacities, Context context,String selectedCapacity, Product product) {
        this.Capacities = capacities;
        this.context = context;
        this.selectedCapacity =selectedCapacity;
        this.product = product;
    }

    @NonNull
    @Override
    public ProductCapacityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product_capacity, parent, false);

        return new ProductCapacityAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCapacityAdapter.ViewHolder holder, int position) {
        String capacity = Capacities.get(position);
        Log.d("ffff", "Color position: "+capacity);
        holder.title.setText(capacity);
        if (capacity.equals(selectedCapacity)) {
            // Đặt màu được chọn
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            // Đặt màu không được chọn
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.background_light));
        }
        holder.itemView.setOnClickListener(v ->{
            selectedCapacity = capacity;
            notifyDataSetChanged();
            ProductAPI.productApi.filterProducts(product.getProduct_Name(),product.getColor(),selectedCapacity).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    Product product = response.body();
                    Log.d("ffff", "filterProduct: " + product);
                    Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                    intent.putExtra("product", product);
                    holder.itemView.getContext().startActivity(intent);

                }
                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    Log.e("++++", t.getMessage());
                    Log.e("====", "Call API Get filter product fail");
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return Capacities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvCapacity);
        }
    }
}
