package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class MyPadgerAdapter extends FragmentStateAdapter {

    private ArrayList<ModalFragment> list;

    public MyPadgerAdapter(@NonNull FragmentActivity fragmentActivity,ArrayList<ModalFragment> list) {
        super(fragmentActivity);
        this.list=list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list.get(position).getFragment();
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
}
