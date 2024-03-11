package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.whatsapp.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CallsFragment callFragment=new CallsFragment();
    ChatFragment chatFragment =new ChatFragment();
    StatusFragment stautsFragment=  new StatusFragment();
    GroupsFragment groupsFragment =new GroupsFragment();
    ArrayList<ModalFragment>list=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        list.add(new ModalFragment(groupsFragment,""));
        list.add(new ModalFragment(chatFragment,"Chat"));
        list.add(new ModalFragment(callFragment,"Call"));
        list.add(new ModalFragment(stautsFragment,"Status"));
        binding.padger.setAdapter(new MyPadgerAdapter(this,list));
        new TabLayoutMediator(binding.tabs, binding.padger, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(list.get(position).getTitle());
            }
        }).attach();

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity3.class));
            }
        });




    }
}