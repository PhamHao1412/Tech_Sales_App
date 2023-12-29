package com.example.macmavenapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.macmavenapp.Activity.ProductsActivity;
import com.example.macmavenapp.Model.Category;
import com.example.macmavenapp.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<Category> categories;
    Context context;

    public CategoryAdapter(List<Category> categoryDomains, Context context) {
        this.categories = categoryDomains;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String id= String.valueOf(categories.get(position).getId());
        Category category = categories.get(position);
        holder.categoryName.setText(categories.get(position).getCategory_Name());
        Glide.with(context)
                .load(categories.get(position).getCategory_Image())
                .into(holder.categoryPic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(holder.itemView.getContext(), LoginActivity.class);
//                intent.putExtra("object", category);
//                holder.itemView.getContext().startActivity(intent);
//                Toast.makeText(context.getApplicationContext(), "Bạn đã chọn "+ category.getCategory_Name(), Toast.LENGTH_SHORT).show();
//                Log.e("0000", category.toString());
                Intent intent = new Intent(context, ProductsActivity.class);
                intent.putExtra("category", category);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
