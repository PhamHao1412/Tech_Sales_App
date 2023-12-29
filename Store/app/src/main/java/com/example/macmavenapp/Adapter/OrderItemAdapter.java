package com.example.macmavenapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.macmavenapp.Model.Order_Item;
import com.example.macmavenapp.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder>{
    List<Order_Item> order_items;
    Context context;

    public OrderItemAdapter(List<Order_Item> order_items, Context context) {
        this.order_items = order_items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_order_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order_Item order_item = order_items.get(position);
        Glide.with(holder.itemView.getContext())
                .load(order_item.getProduct().getProductImage().get(0).getUrl_Image())
                .into(holder.ivProductImage);
        holder.tvProductName.setText(order_item.getProduct().getProduct_Name()+" "+order_item.getProduct().getCapacity());
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        holder.tvPrice.setText(en.format(order_item.getProduct().getPrice()));
        holder.tvColor.setText(order_item.getProduct().getColor());
        holder.tvUnits.setText(order_item.getCount()+"");
        holder.tvTotalPrice.setText(en.format(order_item.getProduct().getPrice()*order_item.getCount()));
    }
    @Override
    public int getItemCount() {
        return  order_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvUnits, tvPrice,tvStatus, tvTotalPrice,tvColor;
        ImageView ivProductImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvUnits = itemView.findViewById(R.id.tvUnits);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvColor = itemView.findViewById(R.id.tvColor);
        }
    }
}
