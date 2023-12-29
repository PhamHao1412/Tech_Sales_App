package com.example.macmavenapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.macmavenapp.Fragment.AllOrderFragment;
import com.example.macmavenapp.Fragment.StatusCompletedFragment;
import com.example.macmavenapp.Fragment.StatusPendingFragment;
import com.example.macmavenapp.Fragment.StatusProcessingFragment;
import com.example.macmavenapp.Fragment.StatusShippedFragment;
import com.example.macmavenapp.Fragment.PayOnDeliveryFragment;

public class OrderFragmentAdapter extends FragmentStateAdapter {


    public OrderFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0)
            return new StatusPendingFragment();
        else if (position == 1) {
            return new StatusProcessingFragment();
        }else if(position == 2){
            return new StatusShippedFragment();
        }
        else
            return new StatusCompletedFragment();
    }
    @Override
    public int getItemCount() {
        return 4;
    }
}
