package com.example.macmavenapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macmavenapp.Model.Order;
import com.example.macmavenapp.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    List<Order> orders;
    Context context;

    public OrderAdapter(List<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_order, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.orderLayout.setBackground(context.getDrawable(R.drawable.order_item_background));
        Order order = orders.get(position);
        if (order.getId()<10)
            holder.tvOderId.setText("Order #0"+order.getId());
        else
            holder.tvOderId.setText("Order #"+order.getId());
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        holder.tvPurchaseDay.setText(simpleDateFormat.format(order.getBooking_Date()));
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        holder.tvTotalPrice.setText(en.format(order.getTotal()));
        holder.tvStatus.setText(order.getStatus());
        holder.tvQuantity.setText(order.getOrder_Item().size()+"");

        holder.itemView.setOnClickListener(v -> {
            if(holder.orderLayout2.getVisibility() == View.VISIBLE){
                holder.orderLayout.setBackground(context.getDrawable(R.drawable.order_item_background));
                holder.orderLayout1.setBackground(null);
                holder.orderLayout2.setVisibility(View.GONE);
                holder.ivHide.setVisibility(View.GONE);
                holder.ivShowMore.setVisibility(View.VISIBLE);
            }
            else{
                holder.orderLayout.setBackground(null);
                holder.orderLayout1.setBackground(context.getDrawable(R.drawable.order_item_background));
                holder.orderLayout2.setVisibility(View.VISIBLE);
                holder.ivHide.setVisibility(View.VISIBLE);
                holder.ivShowMore.setVisibility(View.GONE);
                if (order.getOrder_Item().size()>1)
                    holder.tvTotalItem.setText(order.getOrder_Item().size()+" Items");
                else
                    holder.tvTotalItem.setText(order.getOrder_Item().size()+" Item");
                holder.tvFullName.setText(order.getFullname());
                holder.tvPhoneNumber.setText(order.getPhone());
                holder.tvAddress.setText(order.getAddress());
                holder.tvPayment.setText(order.getPayment_Method());
                holder.orderLayout2.requestFocus();
            }

        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        holder.recyclerViewOrderItem = holder.itemView.findViewById(R.id.recyclerViewOrderItem);
        holder.recyclerViewOrderItem.setLayoutManager(linearLayoutManager);
        holder.orderItemAdapter = new OrderItemAdapter(order.getOrder_Item(), context);
        holder.recyclerViewOrderItem.setAdapter(holder.orderItemAdapter);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOderId, tvPurchaseDay, tvStatus,tvQuantity,tvTotalPrice, tvTotalItem, tvFullName, tvPhoneNumber, tvAddress, tvPayment;
        ImageView ivShowMore, ivHide;
        ConstraintLayout orderLayout, orderLayout1, orderLayout2;
        RecyclerView.Adapter orderItemAdapter;
        RecyclerView recyclerViewOrderItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOderId = itemView.findViewById(R.id.tvOderId);
            tvPurchaseDay = itemView.findViewById(R.id.tvPurchaseDay);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            ivShowMore = itemView.findViewById(R.id.ivShowMore);
            orderLayout = itemView.findViewById(R.id.orderLayout);
            ivHide = itemView.findViewById(R.id.ivHide);
            orderLayout1 = itemView.findViewById(R.id.orderLayout1);
            orderLayout2 = itemView.findViewById(R.id.orderLayout2);
            tvTotalItem = itemView.findViewById(R.id.tvTotalItem);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvPhoneNumber =itemView.findViewById(R.id.tvPhoneNumber);
            tvAddress =itemView.findViewById(R.id.tvAddress);
            tvPayment = itemView.findViewById(R.id.tvPayment);
        }
    }
}
