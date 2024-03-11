package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.example.whatsapp.databinding.ActivityChatsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.ArrayList;
import java.util.Date;

public class ChatsActivity extends AppCompatActivity {
    ActivityChatsBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ArrayList<MassageData> massageData=new ArrayList<>();
    Users users=new Users();
    FirebaseStorage storage;


    Uri image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        String senderId=auth.getUid();
        String recieveId=getIntent().getStringExtra("userId");
        String userName=getIntent().getStringExtra("userName");
        String image=getIntent().getStringExtra("image");

        binding.userNameChat.setText(userName);
//        Picasso.get().load(image).placeholder(R.drawable.baseline_person_24).into(binding.imageUser);

        binding.camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,22);
            }
        });

        binding.backChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatsActivity.this,MainActivity.class));
            }
        });

        AdapterChat adapterChat =new AdapterChat(massageData,this,recieveId);
        binding.recyclerChat.setAdapter(adapterChat);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.recyclerChat.setLayoutManager(layoutManager);
        final String senderRo=senderId+recieveId;
        final String recieveRo=recieveId+senderId;

        database.getReference().child("Chats").child(senderRo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                massageData.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    MassageData massageData2=snapshot1.getValue(MassageData.class);
                    massageData2.setMassageId(snapshot1.getKey());
                    massageData.add(massageData2);
                }
                adapterChat.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String massage=binding.massage.getText().toString();
                if (massage.length()>0){
                    MassageData massageData1 =new MassageData(senderId,massage);
                    massageData1.setTimALong(new Date().getTime());
                    binding.massage.setText("");
                    database.getReference().child("Chats").child(senderRo).push().setValue(massageData1).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            database.getReference().child("Chats").child(recieveRo).push().setValue(massageData1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            });
                        }
                    });
                }
                }


        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData()!=null){
            image=data.getData();


            String senderId=auth.getUid();
            String recieveId=getIntent().getStringExtra("userId");
            final String senderRo=senderId+recieveId;
            final String recieveRo=recieveId+senderId;
            final StorageReference reference=storage.getReference().child("Image_Chat").child(FirebaseAuth.getInstance().getUid());
            reference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                                MassageData massageData=new MassageData(senderId,uri.toString());
                            massageData.setTimALong(new Date().getTime());
                             database.getReference().child("Chats").child(senderRo).push().setValue(massageData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void unused) {
                                     database.getReference().child("Chats").child(recieveRo).push().setValue(massageData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                         @Override
                                         public void onSuccess(Void unused) {

                                         }
                                     });
                                 }
                             });

                        }
                    });
                }
            });
        }
    }
}